package com.plugga.backend.controller

import com.plugga.backend.entity.Pile
import com.plugga.backend.service.PileService
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
@RequestMapping("/api/piles")
class PileController @Autowired
constructor(private val pileService: PileService) {

    @GetMapping("/")
    fun findAll(): List<Pile> {
        return pileService.findAll()
    }

    @GetMapping("/{pileId}")
    fun getPile(@PathVariable pileId: Int): Pile {
        return pileService.findById(pileId) ?: throw RuntimeException("Could not find pile using id: $pileId")
    }

    @GetMapping(value = [""], params = ["deckId"])
    fun getByDeckId(@RequestParam("deckId") deckId: Int): List<Pile> {
        return pileService.findByDeckId(deckId)
    }

    @PostMapping("/")
    fun addPile(@RequestBody pile: Pile): Pile {
        pile.id = 0
        pileService.save(pile)
        return pile
    }

    @PutMapping("/")
    fun updatePile(@RequestBody pile: Pile): Pile {
        pileService.save(pile)
        return pile
    }

    @DeleteMapping("/{pileId}")
    fun deletePile(@PathVariable pileId: Int): String {
        pileService.findById(pileId) ?: throw RuntimeException("Could not find pile using id: $pileId")
        pileService.deleteById(pileId)
        return "Deleted pile with id: $pileId"
    }
}