package com.yolo.dokotlin.item.repository

import com.yolo.dokotlin.item.entity.Book
import com.yolo.dokotlin.item.entity.Computer
import com.yolo.dokotlin.item.repository.ItemRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@Transactional
@ExtendWith(SpringExtension::class)
@ActiveProfiles(value = ["dev"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemRepositoryTests {

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Test
    fun 상품을_저장한다() {

         // given
         var book = Book.of("전공책", 30000, 10, "임준영", "sa134123132")
         var computer = Computer.of("컴퓨터",  500000, 5, "삼성전자")

        // when
        itemRepository.save(book)
        itemRepository.save(computer)

        println("book :: id: ${book.id}, name: ${book.name}, stock: ${book.stock}, author: ${book.author}, isbn: ${book.isbn} ")
        println("computer :: id: ${computer.id}, brandName: ${computer.brandName}, stock: ${computer.stock}, ${computer.name}")

        // then
        assertThat(computer.brandName).isEqualTo("삼성전자")
        assertThat(book.author).isEqualTo("임준영")
    }
}
