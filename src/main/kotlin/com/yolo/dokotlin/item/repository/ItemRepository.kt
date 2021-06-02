package com.yolo.dokotlin.item.repository

import com.yolo.dokotlin.item.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long>
