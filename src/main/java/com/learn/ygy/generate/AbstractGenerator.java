package com.learn.ygy.generate;

import com.learn.ygy.entity.TableEntity;
import com.learn.ygy.freemarker.FreemarkerConfigurationFactory;
import com.learn.ygy.utils.DateUtils;
import com.learn.ygy.utils.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanguangyuan
 * @Description 抽象生成
 * @createTime 2020年12月30日 16:02:00
 */
@Slf4j
public abstract class AbstractGenerator implements IGenerator {
    /**
     * 作者
     */
    protected String author;

    protected TableEntity tableEntity;
    protected String daoPath;
    protected String doPath;
    protected String servicePath;
    protected String dtoPath;
    protected String controllerPath;
    protected String requestVoPath;
    protected String responseVoPath;
    protected Configuration configuration;
    protected String doClassSimpleName;
    protected String doPackage;
    protected String mapperClassSimpleName;
    protected String mapperPackage;
    protected String dtoClassSimpleName;
    protected String dtoPackage;
    protected String serviceClassSimpleName;
    protected String servicePackage;
    protected String controllerClassSimpleName;
    protected String controllerPackage;

    public AbstractGenerator(GeneratorContext context) {
        this.tableEntity = context.getTableEntity();
        this.author = context.getAuthor();
        this.daoPath = context.getDaoPath();
        this.servicePath = context.getServicePath();
        this.controllerPath = context.getControllerPath();
        this.configuration = FreemarkerConfigurationFactory.getInstance();
        this.doPath = daoPath+"\\entity";
        this.dtoPath = servicePath+"\\entity";
        this.requestVoPath = controllerPath+"\\vo\\request";
        this.responseVoPath = controllerPath+"\\vo\\response";
        String tableBeanName = tableEntity.getTableBeanName();
        this.doClassSimpleName = tableBeanName+"DO";
        this.mapperClassSimpleName = tableBeanName+"Mapper";
        this.dtoClassSimpleName = tableBeanName+"DTO";
        this.serviceClassSimpleName=tableBeanName+"Service";
        this.controllerClassSimpleName=tableBeanName+"Controller";
        this.doPackage = getPackageByFilePath(doPath);
        this.mapperPackage = getPackageByFilePath(daoPath);
        this.dtoPackage = getPackageByFilePath(dtoPath);
        this.servicePackage = getPackageByFilePath(servicePath);
        this.controllerPackage = getPackageByFilePath(controllerPath);
    }


    @Override
    public void process() {
        File file = new File(getFilePath() + "\\" + getFileName());

        Writer writer = null;
        try {
            Template template = getTemplate();
            Object data = getTemplateData();
            writer = new FileWriter(file);
            template.process(data, writer);
        } catch (Exception e) {
            log.error("生成文件异常", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("关闭流异常", e);
                }
            }
        }
    }

    protected String getPackageByFilePath(String filePath) {
        String[] strings = filePath.split("java");
        int length = strings.length;
        String packagePath = strings[length - 1];
        String replace = packagePath.replace("\\", ".");
        return replace.substring(1);
    }

    /**
     * 获取生成文件的路径
     *
     * @return
     */
    protected abstract String getFilePath();


    /**
     * 获取类名
     *
     * @return
     */
    protected abstract String getClassSimpleName();
    /**
     * 获取包名称
     * @return
     */
    protected abstract Object getPackageName();
    /**
     * 获取生成文件的名称,默认是.java
     *
     * @return
     */
    protected String getFileName() {
        return getClassSimpleName() + ".java";
    }
    /**
     * 获取模板需要参数数据
     *
     * @return
     */
    private Map<String, Object> getTemplateData() {
        Map<String, Object> result = handleCommonParam();
        result.put("ClassName", getClassSimpleName());
        result.put("PackageName", getPackageName());
        result.put("Author", author);
        result.put("Remarks", tableEntity.getRemarks());
        result.put("Date", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        result.putAll(getTemplateData1());
        return result;
    }



    private Map<String,Object> handleCommonParam(){
        Map<String, Object> result = new HashMap<>(20);
        result.put("DoClass",this.doClassSimpleName);
        result.put("DoPackage",this.doPackage);
        result.put("MapperClass",this.mapperClassSimpleName);
        result.put("MapperPackage",this.mapperPackage);
        result.put("MapperProperty", StringUtil.bigHump2SmallHump(this.mapperClassSimpleName));
        result.put("DtoClass",this.dtoClassSimpleName);
        result.put("DtoPackage",this.dtoPackage);
        result.put("ServiceClass",this.serviceClassSimpleName);
        result.put("ServicePackage",this.servicePackage);
        result.put("ServiceProperty", StringUtil.bigHump2SmallHump(this.serviceClassSimpleName));
        result.put("ControllerClass",this.controllerClassSimpleName);
        result.put("ControllerPackage",this.controllerPackage);
        return result;
    }

    /**
     * 用于子类继承，自定义参数
     *
     * @return
     */
    protected Map<String, Object> getTemplateData1() {
        return new HashMap<>(0);
    }

    /**
     * 获取模板
     *
     * @return
     */
    protected abstract Template getTemplate() throws IOException;


}
