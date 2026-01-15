package com.stech.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * mysql 代码生成器
 * </p>
 */
public class CodeGenerator {
    /**
     * 运行启动
     */
    public static void main(String[] args) {
        //获取控制台的数据
        Scanner scanner = new Scanner(System.in);
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //~~~~~~~~~~~~~~~~~~~~~全局配置~~~~~~~~~~开始~~~~~~~~~
        GlobalConfig gc = new GlobalConfig();
        //System.out.println("请输入文件输出目录的模块或者项目的地址:");
        //String projectPath = scanner.nextLine();
        String projectPath = System.getProperty("user.dir");    //工程根目录
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");        //生成文件的输出目录
        gc.setAuthor("zhangyingdong");                              //作者
        gc.setFileOverride(true);				                //是否覆蓋已有文件 默认值：false
        gc.setOpen(false);                                      //是否打开输出目录 默认值:true
        gc.setBaseColumnList(true);				                //开启 baseColumnList 默认false
        gc.setBaseResultMap(true);				                //开启 BaseResultMap 默认false
        gc.setEntityName("%sEntity");			                //实体命名方式  默认值：null 例如：%sEntity 生成 UserEntity
        gc.setMapperName("%sMapper");			                //mapper 命名方式 默认值：null 例如：%sDao 生成 UserDao
        gc.setXmlName("%sMapper");				                //Mapper xml 命名方式   默认值：null 例如：%sDao 生成 UserDao.xml
        gc.setServiceName("%sService");			                //service 命名方式   默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setServiceImplName("%sServiceImpl");	                //service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setControllerName("%sController");	                //controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        //~~~~~~~~~~~~~~~~~~~~~全局配置~~~~~~~~~~结束~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~数据源配置~~~~~~~~~~开始~~~~~~~~~
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/tcip?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root1234");
        mpg.setDataSource(dsc);
        //~~~~~~~~~~~~~~~~~~~~~数据源配置~~~~~~~~~~结束~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~包配置~~~~~~~~~~开始~~~~~~~~~
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName(scanner("模块名"));
        // pc.setParent("com.stu");
//        System.out.println("请输入模块名:");
//        String name = scanner.nextLine();
        //自定义包配置
        pc.setParent("com.stech.mybatisplus");
        pc.setModuleName(null);
        pc.setMapper("mapper");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        //~~~~~~~~~~~~~~~~~~~~~包配置~~~~~~~~~~结束~~~~~~~~~

        //~~~~~~~~~~~~~~~~~~~~~自定义配置~~~~~~~~~~开始~~~~~~~~~
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + /*pc.getModuleName() + "/" +*/
                        tableInfo.getEntityName() + "Mapper" +
                        StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        //~~~~~~~~~~~~~~~~~~~~~自定义配置~~~~~~~~~~结束~~~~~~~~~

        //这里不自动生成xml,改为自定义生成
        mpg.setTemplate(new TemplateConfig().setXml(null));

        //~~~~~~~~~~~~~~~~~~~~~策略配置~~~~~~~~~~开始~~~~~~~~~
        // 策略配置	数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);	    //表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        //	    strategy.setCapitalMode(true);			            // 全局大写命名 ORACLE 注意
        //	    strategy.setTablePrefix("prefix");		            //表前缀
        	    strategy.setSuperEntityClass("com.stech.core.tenant.mp.TenantEntity");	    //自定义继承的Entity类全称，带包名
        	    strategy.setSuperEntityColumns(new String[] { "id","tenant_group","tenant_id","create_user"
                        ,"create_time","create_dept","create_area","update_user","update_time","status"
                        ,"is_deleted" }); 	//自定义实体，公共字段
        strategy.setEntityLombokModel(true);	                    //【实体】是否为lombok模型（默认 false
        strategy.setRestControllerStyle(true);	                    //生成 @RestController 控制器
        //	    strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");	//自定义继承的Controller类全称，带包名
        //      strategy.setInclude(scanner("表名"));		        //需要包含的表名，允许正则表达式（与exclude二选一配置）
        System.out.println("请输入映射的表名(多个表名英文逗号分割):");
        String tables = scanner.nextLine();
        String[] num = tables.split(",");
        strategy.setInclude(num);                                   // 需要生成的表可以多张表
        //	    strategy.setExclude(new String[]{"test"});          // 排除生成的表
        //如果数据库有前缀，生成文件时是否要前缀acl_
        //      strategy.setTablePrefix("bus_");
        //      strategy.setTablePrefix("sys_");
        strategy.setControllerMappingHyphenStyle(true);	    //驼峰转连字符
        strategy.setTablePrefix(pc.getModuleName() + "_");	//是否生成实体时，生成字段注解
        mpg.setStrategy(strategy);
        //~~~~~~~~~~~~~~~~~~~~~策略配置~~~~~~~~~~结束~~~~~~~~~

        //模板引擎配置,使用Freemarker，默认 Velocity 可选模板引擎 Beetl\
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}


