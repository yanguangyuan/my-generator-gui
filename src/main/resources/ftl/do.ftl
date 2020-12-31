package ${PackageName};


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.TableName;
<#list PropertyJavaTypeSet as PropertyJavaType >
import ${PropertyJavaType};
</#list>


/**
* @Author  ${Author}
* @Description  ${Remarks}
* @createTime  ${Date}
*/
@Getter
@Setter
@ToString
@TableName("${TableName}")
public class ${ClassName} {
<#list ColumnEntities as ColumnEntity>
    /**
    * ${ColumnEntity.remarks}
    **/
    private ${ColumnEntity.propertyTypeName} ${ColumnEntity.propertyName};

</#list>

}