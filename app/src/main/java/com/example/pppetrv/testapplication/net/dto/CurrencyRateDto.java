package com.example.pppetrv.testapplication.net.dto;

import com.example.pppetrv.testapplication.model.AccountCurrency;
import com.example.pppetrv.testapplication.util.DigitsConverter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/*
 * The class contains a list of various information
 *  on the currency of methods to get and to set new values.
 */
@SuppressWarnings("serial")
@Root
public class CurrencyRateDto implements Serializable, Cloneable {

	@Element(name="buy_good_id")
	public int buyGoodId;
	@Element(name="sell_good_id")
	public int sellGoodId;
	@Element(name="buy_amount")
	public double buyAmount;
	@Element(name="sell_amount")
	public double sellAmount;
	@Element(name="sell_amount_delta")
	public double sellAmountDelta;
	@Element(name="buy_amount_delta")
	public double buyAmountDelta;
	@Element(name="buy_good_name")
	public AccountCurrency buyGoodName;
	@Element(name="sell_good_name")
	public AccountCurrency sellGoodName;
	@Element(name="buy_good_title")
	public String buyGoodTitle;
	@Element(name="sell_good_title")
	public String sellGoodTitle;
	@Element(name="item_order")
	public int itemOrder;
	@Element(name="buy_good_scale")
	public double buyGoodScale;

	@Override
    public int hashCode() {
    	int code = super.hashCode();
    	if (buyGoodName != null) {
        	code = code ^ buyGoodName.hashCode();
        }
    	if (sellGoodName != null) {
        	code = code ^ sellGoodName.hashCode();
        }
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CurrencyRateDto)) {
            return false;
        }

        CurrencyRateDto rate = (CurrencyRateDto) obj;
        boolean result = true;
        if (buyGoodName != null) {
        	result &= buyGoodName.equals(rate.buyGoodName);
        }
        if (sellGoodName != null) {
        	result &= sellGoodName.equals(rate.sellGoodName);
        }
        return result;
    }

    @Override
    public CurrencyRateDto clone() {
     	try {
    		return (CurrencyRateDto) super.clone();
    	} catch(Exception e) {}
		return null;
    }
}
