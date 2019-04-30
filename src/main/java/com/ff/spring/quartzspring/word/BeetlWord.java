package com.ff.spring.quartzspring.word;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class BeetlWord {
    /**
     * 生成静态html
     * @param tlPath 模板路径
     * @param paramMap 参数
     * @param htmlPath html文件保存路径
     */
    public static void makeHtml(String tlPath, Map<String, Object> paramMap, String htmlPath) {
        PrintWriter pw = null;
        try {
            /*GroupTemplate groupTemplate = regiseter();

            Template template = groupTemplate.getTemplate(tlPath);

            //添加数据到上下文中
            Set<String> keys = paramMap.keySet();
            for (String key : keys) {
                template.binding(key, paramMap.get(key));
            }

            pw = new PrintWriter(htmlPath,"utf-8");
            template.renderTo(pw);//template.renderTo(os);*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pw != null ){
                pw.close();
            }
        }
    }
}
