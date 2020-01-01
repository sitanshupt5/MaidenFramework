package Utitlities;


import java.io.IOException;

public class DataProviderMapper {

    public Object[][] dataMapper(String filepath, String sheetname) throws IOException
    {
        ExcelUtils e = new ExcelUtils();
        e.setFilePath(filepath);
        Object[][] map =  e.excelDataDriver(sheetname);
        return map;

    }
}
