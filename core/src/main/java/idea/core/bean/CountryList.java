/**
 * 
 */
package idea.core.bean;

import java.util.List;

/**
 * @author sriik
 *
 */
public class CountryList {

	private String status;
	private List<CountryDetail> data;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the data
	 */
	public List<CountryDetail> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<CountryDetail> data) {
		this.data = data;
	}
	
	
}
