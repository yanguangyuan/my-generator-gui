package com.learn.ygy.generate;

import com.learn.ygy.entity.TableEntity;
import com.learn.ygy.freemarker.FreemarkerConfigurationFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
    protected String servicePath;
    protected String controllerPath;
    protected Configuration configuration;

    public AbstractGenerator(TableEntity tableEntity, String author, String daoPath, String servicePath, String controllerPath) {
        this.tableEntity = tableEntity;
        this.author = author;
        this.daoPath = daoPath;
        this.servicePath = servicePath;
        this.controllerPath = controllerPath;
        configuration = FreemarkerConfigurationFactory.getInstance();
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

    protected String getPackageByFilePath() {
        String[] strings = getFilePath().split("java");
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
     * 获取生成文件的名称,默认是.java
     *
     * @return
     */
    protected String getFileName() {
        return getClassName() + ".java";
    }

    /**
     * 获取类名
     * @return
     */
    protected abstract String getClassName();

    /**
     * 获取模板需要参数数据
     *
     * @return
     */
    protected abstract Object getTemplateData();

    /**
     * 获取模板
     *
     * @return
     */
    protected abstract Template getTemplate() throws IOException;


}
