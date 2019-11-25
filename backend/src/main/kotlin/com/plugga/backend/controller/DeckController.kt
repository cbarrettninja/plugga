package com.plugga.backend.controller

import com.plugga.backend.entity.Deck
import com.plugga.backend.service.DeckService
import java.sql.Timestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/decks")
class DeckController @Autowired
constructor(private val deckService: DeckService) {

    @GetMapping("/")
    fun findAll(): List<Deck> {
        return deckService.findAll()
    }

    @GetMapping("/{deckId}")
    fun getDeck(@PathVariable deckId: Int): Deck {
        return deckService.findById(deckId) ?: throw RuntimeException("Could not find deck using id: $deckId")
    }

    @GetMapping(value = [""], params = ["userId"])
    fun getByUserId(@RequestParam("userId") userId: Int): List<Deck> {
        return deckService.findByUserId(userId)
    }

    @PostMapping("/")
    fun addDeck(@RequestBody deck: Deck): Deck {
        deck.id = 0
        deck.dateCreated = Timestamp(System.currentTimeMillis())
        deckService.save(deck)
        return deck
    }

    @PutMapping("/")
    fun updateDeck(@RequestBody deck: Deck): Deck {
        deckService.save(deck)
        return deck
    }

    @DeleteMapping("/{deckId}")
    fun deleteDeck(@PathVariable deckId: Int): String {
        deckService.findById(deckId) ?: throw RuntimeException("Could not find deck using id: $deckId")
        deckService.deleteById(deckId)
        return "Deleted deck with id: $deckId"
    }
}