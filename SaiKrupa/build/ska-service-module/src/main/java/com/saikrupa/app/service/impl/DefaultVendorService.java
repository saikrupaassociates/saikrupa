package com.saikrupa.app.service.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.ContactPerson;
import com.saikrupa.app.dto.VendorData;
import com.saikrupa.app.service.VendorService;



public class DefaultVendorService implements VendorService {

	public DefaultVendorService() {
		// TODO Auto-generated constructor stub
	}

	public VendorData createVendor(VendorData vendorData) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		connection.setAutoCommit(false);

		final String SQL_CREATE_VENDOR = "INSERT INTO VENDOR (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY) VALUES(?,?,?)";
		PreparedStatement vendorStatement = connection.prepareStatement(SQL_CREATE_VENDOR,
				PreparedStatement.RETURN_GENERATED_KEYS);
		vendorStatement.setString(1, vendorData.getName());
		vendorStatement.setString(2, vendorData.getPrimaryContactNo());
		vendorStatement.setString(3, vendorData.getSecondaryContactNo());
		int rowCount = vendorStatement.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = vendorStatement.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			vendorData.setCode(code);
		} else {
			vendorStatement.close();
			throw new Exception("Vendor Data could not be loaded");
		}
		vendorStatement.close();

		final String SQL_CREATE_CONTACT = "INSERT INTO CONTACT_PERSON "
				+ " (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, TYPE_CODE, VENDOR_CODE) " + " VALUES(?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CONTACT,
				PreparedStatement.RETURN_GENERATED_KEYS);
		for (ContactPerson cp : vendorData.getContactPersons()) {
			statement.setString(1, cp.getName());
			statement.setString(2, cp.getPrimaryContact());
			statement.setString(3, cp.getSecondaryContact());
			statement.setString(4, "50001");
			statement.setString(5, vendorData.getCode());
			int count = statement.executeUpdate();
			if (count > 0) {
				ResultSet keys = statement.getGeneratedKeys();
				keys.next();
				String code = keys.getString(1);
				cp.setCode(code);
				cp.setVendor(vendorData);

				final String SQL_CREATE_CONTACT_ADDRESS = "insert into address "
						+ " (ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, ADDRESS_LANDMARK, CONTACT_PERSON_CODE) "
						+ " values(?,?,?,?,?)";
				PreparedStatement addressStatement = connection.prepareStatement(SQL_CREATE_CONTACT_ADDRESS,
						PreparedStatement.RETURN_GENERATED_KEYS);
				addressStatement.setString(1, cp.getAddress().getLine1());
				addressStatement.setString(2, cp.getAddress().getLine2());
				addressStatement.setString(3, cp.getAddress().getState());
				addressStatement.setString(4, cp.getAddress().getLandmark());
				addressStatement.setString(5, cp.getCode());
				int addrCount = addressStatement.executeUpdate();
				if (addrCount > 0) {
					ResultSet addressKey = statement.getGeneratedKeys();
					addressKey.next();
					String addressCode = keys.getString(1);
					cp.getAddress().setCode(addressCode);
				}
			}

		}
		connection.commit();
		return vendorData;
	}

	public VendorData updateVendor(VendorData vendorData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<VendorData> getAllVendors() {
		String sql_sel_vendor = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM VENDOR";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<VendorData> vendorList = new ArrayList<VendorData>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql_sel_vendor);
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				VendorData vendor = new VendorData();
				vendor.setCode(records.getString(1));
				vendor.setName(records.getString(2));
				vendor.setPrimaryContactNo(records.getString(3));
				vendor.setSecondaryContactNo(records.getString(4));
				List<ContactPerson> contacts = findContactPersonsByVendorCode(vendor);
				vendor.setContactPersons(contacts);
				vendorList.add(vendor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vendorList;
	}

	public List<AddressData> findAddressByContactPerson(String contactPersonCode) {
		String sql_sel_address = "SELECT ADDRESS_CODE, ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, ADDRESS_LANDMARK FROM ADDRESS WHERE CONTACT_PERSON_CODE = ?";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<AddressData> addresses = new ArrayList<AddressData>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql_sel_address);
			stmt.setString(1, contactPersonCode);
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				AddressData address = new AddressData();
				address.setCode(records.getString(1));
				address.setLine1(records.getString(2));
				address.setLine2(records.getString(3));
				address.setZipCode(records.getString(4));
				address.setLandmark(records.getString(5));
				addresses.add(address);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addresses;
	}

	public VendorData findVendorByCode(String vendorCode) {
		String sql_sel_vendor = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM VENDOR WHERE CODE = ? ";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		VendorData vendor = new VendorData();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql_sel_vendor);
			stmt.setInt(1, Integer.valueOf(vendorCode).intValue());
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				
				vendor.setCode(records.getString(1));
				vendor.setName(records.getString(2));
				vendor.setPrimaryContactNo(records.getString(3));
				vendor.setSecondaryContactNo(records.getString(4));
				List<ContactPerson> contacts = findContactPersonsByVendorCode(vendor);
				vendor.setContactPersons(contacts);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vendor;
	}

	private List<ContactPerson> findContactPersonsByVendorCode(VendorData vendor) {
		String sql_sel_contact = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY, TYPE_CODE FROM CONTACT_PERSON WHERE VENDOR_CODE = ?";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<ContactPerson> contacts = new ArrayList<ContactPerson>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql_sel_contact);
			stmt.setString(1, vendor.getCode());
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				ContactPerson cp = new ContactPerson();
				cp.setCode(records.getString(1));
				cp.setName(records.getString(2));
				cp.setPrimaryContact(records.getString(3));
				cp.setSecondaryContact(records.getString(4));
				cp.setVendor(vendor);
				contacts.add(cp);
				List<AddressData> addresses = findAddressByContactPerson(cp.getCode());
				if(!addresses.isEmpty()) {
					cp.setAddress(addresses.get(0));
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contacts;
	}

}
