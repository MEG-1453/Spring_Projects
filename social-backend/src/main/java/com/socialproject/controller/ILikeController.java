package com.socialproject.controller;

import com.socialproject.dtos.LikeSaveRequestDTO;

public interface ILikeController {

	String toggleLike(LikeSaveRequestDTO request);
}
