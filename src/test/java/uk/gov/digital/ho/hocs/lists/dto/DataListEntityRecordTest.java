package uk.gov.digital.ho.hocs.lists.dto;

import org.junit.Test;
import uk.gov.digital.ho.hocs.lists.model.DataListEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class DataListEntityRecordTest {

    @Test
    public void createWithOneConstructor() throws Exception {
        DataListEntity dataListEntity = new DataListEntity(new DataListEntityRecord("Text", "Text"));
        DataListEntityRecord record = DataListEntityRecord.create(dataListEntity);

        assertThat(record.getText()).isEqualTo(dataListEntity.getText());
        assertThat(record.getValue()).isEqualTo("TEXT");
        assertThat(record.getProperties()).hasSize(0);
    }

    @Test
    public void createWithNoEntitiesNoProperties() throws Exception {
        DataListEntity dataListEntity = new DataListEntity(new DataListEntityRecord("Text", "Value"));
        DataListEntityRecord record = DataListEntityRecord.create(dataListEntity);

        assertThat(record.getText()).isEqualTo(dataListEntity.getText());
        assertThat(record.getValue()).isEqualTo("VALUE");
        assertThat(record.getProperties()).hasSize(0);
    }

}