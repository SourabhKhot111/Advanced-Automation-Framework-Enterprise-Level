package org.opencart.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opencart.constants.FrameworkConstants;
import org.opencart.utilities.ExcelUtilities;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

/**
 * MethodInterceptor class implements {@link IMethodInterceptor} and is
 * responsible for identifying the executable tests based on the test data
 * provided.
 * 
 * @author Shriraj Ghorpade
 */
public class MethodInterceptor implements IMethodInterceptor {

	/**
	 * This methods overrides intercept method of {@link IMethodInterceptor} class.
	 * This method accepts the list of methods with @Test tag and identifies the
	 * list of methods that are to be executed from excel data sheet. Test methods
	 * which are part of this list are executed.
	 */
	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
		List<Map<String, String>> list = ExcelUtilities.getData(FrameworkConstants.getRunManagerSheet());

		for (int i = 0; i < methods.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (methods.get(i).getMethod().getMethodName().equals(list.get(j).get("test name"))
						&& list.get(j).get("execute").equalsIgnoreCase("yes")) {
					methods.get(i).getMethod().setDescription(list.get(j).get("test description"));
					methods.get(i).getMethod().setPriority(Integer.parseInt(list.get(j).get("priority")));
					result.add(methods.get(i));
				}
			}
		}
		return result;
	}

}
