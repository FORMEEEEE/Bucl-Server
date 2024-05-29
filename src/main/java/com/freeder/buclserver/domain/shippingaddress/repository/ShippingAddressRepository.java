package com.freeder.buclserver.domain.shippingaddress.repository;

import com.freeder.buclserver.admin.주문관리.dto.주문관리Dto;
import com.freeder.buclserver.admin.주문관리.dto.주문관리가공전Dto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.shipping.entity.Shipping;
import com.freeder.buclserver.domain.shippingaddress.entity.ShippingAddress;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

	ShippingAddress findByShipping(Shipping shipping);

		@Query("select new com.freeder.buclserver.admin.주문관리.dto.주문관리가공전Dto(" +
				"co.id," +
				"pd.id," +
				"pd.name," +
				"sa.address," +
				"sa.addressDetail," +
				"sa.recipientName," +
				"sa.contactNumber," +
				"co.createdAt" +
				") " +
				"from ShippingAddress sa " +
				"left join sa.shipping sp " +
				"left join sp.consumerOrder co " +
				"left join co.product pd " +
				"where sa.recipientName = :name "
		)
		List<주문관리가공전Dto> ordersByName(String name);


	@Query("select new com.freeder.buclserver.admin.주문관리.dto.주문관리가공전Dto(" +
			"co.id," +
			"pd.id," +
			"pd.name," +
			"sa.address," +
			"sa.addressDetail," +
			"sa.recipientName," +
			"sa.contactNumber," +
			"co.createdAt" +
			") " +
			"from ShippingAddress sa " +
			"left join sa.shipping sp " +
			"left join sp.consumerOrder co " +
			"left join co.product pd " +
			"where sa.contactNumber = :phoneNumber"


	)
	List<주문관리가공전Dto> findByContactNumber(String phoneNumber);

	//Optional<ShippingAddress> findByShipping(Shipping shipping);
}
