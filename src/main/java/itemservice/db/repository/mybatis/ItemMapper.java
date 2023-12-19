package itemservice.db.repository.mybatis;

import itemservice.db.domain.Item;
import itemservice.db.repository.ItemSearchCond;
import itemservice.db.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    // @Param 은 parameter 가 2개 이상일때
    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

}
