package com.freeder.buclserver.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.freeder.buclserver.admin.고객관리.dto.유저프로필Dto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freeder.buclserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findBySocialIdAndDeletedAtIsNull(String socialId);

	Optional<User> findByRefreshToken(String refreshToken);

	Optional<User> findByIdAndDeletedAtIsNull(Long userId);

	boolean existsByIdAndDeletedAtIsNull(Long userId);

	Optional<User> findBySocialId(String socialId);



	@Query("select new com.freeder.buclserver.admin.고객관리.dto.유저프로필Dto(" +
			"us.id, " +
			"us.userName," +
			"us.cellPhone," +
			"rw.rewardSum," +
			"us.businessRewardRate" +
			") " +
			"from User us " +
			"left join us.rewards rw " +
			"where us.userName = :name "
			)
	List<유저프로필Dto> UserName(String name);


	@Query("select new com.freeder.buclserver.admin.고객관리.dto.유저프로필Dto(" +
			"us.socialId, " +
			"us.userName," +
			"us.cellPhone," +
			"rw.rewardSum," +
			"us.businessRewardRate" +
			") " +
			"from User us " +
			"left join us.rewards rw " +
			"where us.socialId = :id "
	)
	List<유저프로필Dto> UserId(Long id);

}
