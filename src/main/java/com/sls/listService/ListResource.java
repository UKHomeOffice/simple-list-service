package com.sls.listService;

import com.sls.listService.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ListResource {
    final ListRepository repo;

    @Autowired
    public ListResource(ListRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/list/{reference}", method = RequestMethod.GET)
    public ResponseEntity<DataListRecord> getListByReference(@PathVariable("reference") String reference) {
        log.info("List \"{}\" requested", reference);
        try {
            DataList list = repo.findOneByReference(reference);
            return ResponseEntity.ok(toDataList(list));
        }
        catch(NullPointerException e)
        {
            log.info("List \"{}\" not found", reference);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/legacy/list/{reference}", method = RequestMethod.GET)
    public ResponseEntity<LegacyDataListEntityRecord[]> getLegacyListByReference(@PathVariable("reference") String reference) {
        log.info("List \"{}\" requested", reference);
        try {
            DataList list = repo.findOneByReference(reference);
            return ResponseEntity.ok(toLegacyDataList(list));
        }
        catch(NullPointerException e)
        {
            log.info("List \"{}\" not found", reference);
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<DataListRecord> getListByReference(@RequestBody DataList dataList) {

        repo.save(dataList);
        return ResponseEntity.ok().build();
    }



    private DataListRecord toDataList(DataList list) {
        List<DataListEntityRecord> entities = new ArrayList<>();

        if(list.getEntities() != null) {
            entities = list.getEntities().stream().map(r -> asRecord(r)).collect(Collectors.toList());
        }
        return new DataListRecord(list.getReference(), entities);
    }

    private DataListEntityRecord asRecord(DataListEntity listEntity){
        List<DataListEntityRecord> entityRecords = new ArrayList<>();
        List<DataListEntityRecordProperties> properties = new ArrayList<>();

        if(listEntity.getProperties() != null) {
            properties = listEntity.getProperties()
                    .stream()
                    .map(r -> new DataListEntityRecordProperties(r.getProperty(), r.getValue()))
                    .collect(Collectors.toList());
        }

        if(listEntity.getSubEntities() != null){
            entityRecords = listEntity.getSubEntities()
                    .stream()
                    .map(r -> asRecord(r))
                    .collect(Collectors.toList());
        }

        return new DataListEntityRecord(listEntity.getValue(), listEntity.getReference(), entityRecords, properties);
    }

    private LegacyDataListEntityRecord[] toLegacyDataList(DataList list) {
        List<LegacyTopicListItem> entities = new ArrayList<>();

        if(list.getEntities() != null) {
            entities = list.getEntities().stream().map(r -> asLegacyRecord(r)).collect(Collectors.toList());
        }
        return entities.toArray(new LegacyDataListEntityRecord[0]);
    }

    private LegacyTopicListItem asLegacyRecord(DataListEntity listEntity){

        return new LegacyTopicListItem(listEntity.getValue(), listEntity.getReference());
    }
}