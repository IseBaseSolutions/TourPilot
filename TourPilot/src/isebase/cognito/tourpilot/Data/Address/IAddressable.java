package isebase.cognito.tourpilot.Data.Address;

import isebase.cognito.tourpilot.NewData.NewAddress.NewAddress;

public interface IAddressable {
	Address getAddress();
	NewAddress getNewAddress();
	String getFullName();
}
