package ${PackageName};


import org.springframework.stereotype.Repository;
import com.keytop.superpark.mc.base.dao.BaseDao;
import ${DoPackage}.${DoClass};

/**
* @Author  ${Author}
* @Description  ${Remarks}
* @createTime  ${Date}
*/
@Repository
public class ${ClassName} extends BaseDao{
<#assign select_is_first=true/>
<#assign insert_columns_is_first=true/>
<#assign insert_properties_is_first=true/>
<#assign update_is_first=true/>

    /**
    * 不带where条件的查询语句
    */
    private static final String SQL_QUERY_WITH_NO_WHERE="SELECT <#list ColumnEntities as ColumnEntity>${select_is_first?string("",",")}${ColumnEntity.columnName}<#assign select_is_first=false/></#list>  FROM ${TableName} ";
    /**
    * insert
    */
    private static final String SQL_INSERT="INSERT INTO ${TableName}(<#list ColumnEntities as ColumnEntity>${insert_columns_is_first?string("",",")}${ColumnEntity.columnName}<#assign insert_columns_is_first=false/></#list>)"+
                                           "VALUES(<#list ColumnEntities as ColumnEntity>${insert_properties_is_first?string("",",")}:${ColumnEntity.propertyName}<#assign insert_properties_is_first=false/></#list>)";
    /**
    * 不带where的更新语句--注意带where
    */
    private static final String SQL_UPDATE_WITH_NO_WHERE="UPDATE ${TableName} SET <#list ColumnEntities as ColumnEntity>${update_is_first?string("",",")}${ColumnEntity.columnName}=:${ColumnEntity.propertyName}<#assign update_is_first=false/></#list>";

}