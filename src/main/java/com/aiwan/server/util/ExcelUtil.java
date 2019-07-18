package com.aiwan.server.util;


import com.aiwan.server.publicsystem.annotation.CellMapping;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExcelUtil
{

    static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * jar包类型
     */
    private static final String JAR = "jar";

    /**
     * file类型
     */
    private static final String FILE = "file";

    /**
     * 解析文件
     *
     * @param filePath 文件路径
     * @param cls      泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> analysisExcelFile(String filePath, Class<T> cls) throws IllegalAccessException, InstantiationException {

        return analysisExcelFile(filePath, 0, 0, cls);
    }

    /**
     * 根据文件相对路径解析xls文件
     *
     * @param filePath 文件相对路径
     * @param cls      泛型
     * @return
     */
    public static <T> List<T> analysisWithRelativePath(String filePath, Class<T> cls) throws IllegalAccessException, InstantiationException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(filePath);
        logger.info("url:" + url);
        if (url != null) {
            String type = url.getProtocol();
            logger.debug("type :" + type);
            if (FILE.equals(type)) {
                return analysisExcelFile(url.getPath(), 0, 0, cls);
            } else if (JAR.equals(type)) {
                //jar文件解析
                logger.debug(filePath + "   :" + url.getPath());
                String path = url.getPath();
                //分割路径
                String[] jarInfo = path.split("!");
                String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
                String packagePath = jarInfo[1].substring(1);
                JarFile jarFile = null;
                try {
                    //获取jar文件根路径
                    jarFile = new JarFile(jarFilePath);
                    Enumeration<JarEntry> entrys = jarFile.entries();
                    for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); ) {
                        //这个循环会读取jar包中所有文件，包括文件夹
                        //jarEntry就是我们读取的jar包中每一个文件了，包括目录
                        JarEntry jarEntry = e.nextElement();
                        if (jarEntry.getName().contains(filePath)) {
                            //getName()会获取文件全路径名称
                            InputStream is = jarFile.getInputStream(jarEntry);
                            Workbook wb = new HSSFWorkbook(is);
                            return analysisExcelByWorkbook(wb, 0, 0, cls);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }
    /**
     * 解析文件
     *
     * @param filePath  文件路径
     * @param bgnIgnore 开头忽略多少行
     * @param endIgnore 结尾忽略多少行
     * @param cls       泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> analysisExcelFile(String filePath, int bgnIgnore, int endIgnore, Class<T> cls) throws IllegalAccessException, InstantiationException
    {
        // 表格数据对象
        List<T> modelList = new ArrayList<T>();

        // 用来存放表格中数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        // 判断文件是否存在
        if (!new File(filePath).exists())
        {
            logger.debug(filePath+"：为空");
            return null;
        }
        // 读取excel文件信息
        Workbook wb = ExcelUtil.readExcel(filePath);

        if (null == wb)
        {

            return modelList;
        }

        return analysisExcelByWorkbook(wb,0,0,cls);
    }

    /**
     * 读取excel
     *
     * @param filePath
     * @return
     */
    public static Workbook readExcel(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));

        InputStream is = null;
        try
        {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString))
            {
                return new HSSFWorkbook(is);
            }
            else if (".xlsx".equals(extString))
            {
                return new XSSFWorkbook(is);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != is)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取Cell
     *
     * @param cell
     * @return
     */
    public static Object getCellFormatValue(Cell cell)
    {
        Object cellValue;

        //判断cell类型
        switch (cell.getCellType())
        {
            case Cell.CELL_TYPE_NUMERIC:
            {
                cellValue = cell.getNumericCellValue();
                break;
            }
            case Cell.CELL_TYPE_FORMULA:
            {
                //判断cell是否为日期格式
                if (DateUtil.isCellDateFormatted(cell))
                {
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }
                else
                {
                    //数字
                    cellValue = cell.getNumericCellValue();
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:
            {
                cellValue = cell.getRichStringCellValue().getString();
                break;
            }
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 根据Workbook解析文件
     * @param wb      excel文件
     * @param bgnIgnore 开头忽略多少行
     * @param endIgnore 结尾忽略多少行
     * @param cls       泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> analysisExcelByWorkbook(Workbook wb, int bgnIgnore, int endIgnore, Class<T> cls) throws IllegalAccessException, InstantiationException {
        // 表格数据对象
        List<T> modelList = new ArrayList<T>();

        // 用来存放表格中数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (null == wb) {

            return modelList;
        }

        // 获取sheet总数
        int sheetNum = wb.getNumberOfSheets();

        for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {
            // 获取sheet
            Sheet sheet = wb.getSheetAt(sheetIndex);

            // 获取最大行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            // 除去结尾忽略的行数
            rowNum -= endIgnore;

            // 获取开始行(0 + bgnIgnore)
            Row titleRow = sheet.getRow(bgnIgnore);
            if (null == titleRow) {
                continue;
            }
            // 获取明细列数
            int colNum = titleRow.getPhysicalNumberOfCells();

            // 有效的数据开始行，即标题行+1
            int rowBgn = bgnIgnore + 1;
            // 遍历明细
            for (int rowIndex = rowBgn; rowIndex < rowNum; rowIndex++) {
                // 行map
                Map<String, Object> map = new HashMap<String, Object>(16);
                // 行信息
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    // 遍历行并获取到返回值
                    for (int cellIndex = 0; cellIndex < colNum; cellIndex++) {
                        Cell titleCell = titleRow.getCell(cellIndex);
                        Cell cell = row.getCell(cellIndex);
                        // 标题
                        String titleCellData = (String) ExcelUtil.getCellFormatValue(titleCell);
                        if (cell == null) {
                            map.put(titleCellData, null);
                            continue;
                        }
                        // 数据
                        Object cellData = ExcelUtil.getCellFormatValue(cell);
                        map.put(titleCellData, cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }
        }
        // 将对账文件信息存储于类中
        for (Map<String, Object> map : list) {
            T model = cls.newInstance();

            // 获取类字段
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 获取字段上的注解
                Annotation[] annotations = field.getAnnotations();
                if (annotations.length == 0) {
                    continue;
                }

                for (Annotation an : annotations) {
                    field.setAccessible(true);
                    // 若扫描到CellMapping注解
                    if (an.annotationType().getName().equals(CellMapping.class.getName())) {
                        // 获取指定类型注解
                        CellMapping column = field.getAnnotation(CellMapping.class);
                        String mappedName = column.name();

                        // 获取model属性的类型
                        Class<?> modelType = field.getType();
                        // 获取map中的数据
                        Object value = map.get(mappedName);
                        DecimalFormat df = new DecimalFormat("######0");
                        // 匹配字段类型
                        if (null != value) {
                            // 获取map中存主的字段的类型
                            Class<?> cellType = value.getClass();
                            // 处理int类型不匹配问题
                            if (value instanceof Double && (modelType == int.class || modelType == Integer.class)) {
                                value = Integer.valueOf(df.format(value));
                            }
                            if (value instanceof Double && (modelType == Long.class || modelType == long.class)) {
                                value = ((Double) value).longValue();
                            }
                            if ((cellType == double.class || cellType == String.class) && (modelType == int.class || modelType == Integer.class)) {
                                value = Integer.valueOf(value.toString());
                            }
                            // 处理double/bigDecimal类型不匹配问题
                            if ((cellType == double.class || cellType == String.class && modelType == java.math.BigDecimal.class)) {
                                // 不使用bigDecimal(double),否则bigDecimal(0.1)有惊喜
                                value = new BigDecimal(value.toString());
                            }
                            if (cellType == String.class && modelType == double.class) {
                                value = Double.valueOf(value.toString());
                            }
                            // 处理String类型不匹配问题
                            if (cellType != String.class && modelType == String.class) {
                                value = value.toString();
                            }
                        }
                        field.set(model, value);
                    }
                }
                field.setAccessible(false);
            }
            modelList.add(model);
        }

        return modelList;
    }


}