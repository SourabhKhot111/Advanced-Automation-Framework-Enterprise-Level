package org.opencart.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencart.constants.FrameworkConstants;
import org.opencart.exceptions.FileOperationsFailedException;
import org.opencart.exceptions.InvalidExcelPathException;

/**
 * ExcelUtilities class fetches the data from excel sheet.
 * 
 * @author Shriraj Ghorpade
 */
public final class ExcelUtilities {

	private ExcelUtilities() {
	}

	/**
	 * This method accepts the sheetname from which testdata is to be fetched. The
	 * data is returned as list of maps.
	 * 
	 * @param sheetName - sheet from which data is to be fetched
	 * @return List of maps
	 */
	public static List<Map<String, String>> getData(String sheetName) {
		List<Map<String, String>> list = null;

		try (FileInputStream fis = new FileInputStream(FrameworkConstants.getTestDataExcelFile());
				XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int lastRow = sheet.getLastRowNum();
			int lastCol = sheet.getRow(0).getLastCellNum();

			HashMap<String, String> map = null;
			list = new ArrayList<>();

			for (int i = 1; i <= lastRow; i++) {
				map = new HashMap<>();
				for (int j = 0; j < lastCol; j++) {
					String key = sheet.getRow(0).getCell(j).getStringCellValue();
					String value = sheet.getRow(i).getCell(j).getStringCellValue();
					map.put(key, value);
				}
				list.add(map);
			}

		} catch (FileNotFoundException e) {
			throw new InvalidExcelPathException("Invalid excel file path " + FrameworkConstants.getTestDataExcelFile()
					+ ". Please check excel file path in FrameworkConstants.java class", e);
		} catch (IOException e) {
			throw new FileOperationsFailedException("Some I/O exception occured while working with excel file.", e);
		}
		return list;
	}
}
