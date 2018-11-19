package com.example.pppetrv.testapplication.net.dto;

import com.example.pppetrv.testapplication.model.AccountCurrency;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * The class contains the data of the office,
 *  as well as class data CurrencyRateDto (data exchange)
 */
@Root
public class BankDepartmentDto implements Serializable {
	@Element(name="name", required = false)
	public String name;
	@Element(name="description", required = false)
	public String description;
	@Element(name="time", required = false)
	public String timeWork;
	@Element(name="number", required = false)
	public long number;
	@Element(name="id", required = false)
	public long id;
	@Element(name="longitude", required = false)
	public double longitude;
	@Element(name="lattitude", required = false)
	public double latitude;
	@Element(name="previous_time", required = false)
	public String previousTime;
	@Element(name="next_time", required = false)
	public String nextTime;
	//@Element(name="from_time")
	public String fromTime;
	@Element(name="national_currency", required = false)
	public AccountCurrency nationalCurrency;
	@Path("from_time")
	@ElementList(name="rates", entry="item", type=CurrencyRateDto.class)
	public List<CurrencyRateDto> currencyRates = new ArrayList<CurrencyRateDto>();

	public int ratesCount() {
		int count = 0;
		if (currencyRates != null) {
			count = currencyRates.size();
		}
		return count;
	}
}
