package com.reddit.reddit_clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.reddit_clone.dto.RegisterRequest;
import com.reddit.reddit_clone.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "created", expression = "java(java.time.Instant.now())")
	@Mapping(target = "enabled", constant = "false")
	User registerRequestToUser(RegisterRequest registerRequest);
}