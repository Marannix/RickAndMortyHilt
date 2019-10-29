package com.example.rickandmorty

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CharactersDatabaseTest {
    // TODO: Improve test after creating usecase and repository
//    private var characters: List<CharactersResults> = emptyList()
//    private val applicationContext = ApplicationProvider.getApplicationContext<Context>()
//    private var characterDao: CharactersDao? = null
//    private var database: ApplicationDatabase = ApplicationDatabase.DatabaseProvider.getInstance(applicationContext)!!
//
//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        ApplicationDatabase.DatabaseProvider.TEST_MODE = true
//        characterDao = database.charactersDao()
//    }
//
//    @Test
//    fun should_insert_characters() {
//        characters = listOf(
//            CharactersResults(
//                id = "1",
//                name = "Rick Sanchez",
//                status = "Alive",
//                species = "Human",
//                gender = "Male",
//                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
//                location = CharacterLocation("Earth (Replacement Dimension)"),
//                episode = listOf("https://rickandmortyapi.com/api/episode/1")
//            )
//        )
//        characterDao?.insertCharacters(characters)
//        val charactersTest = characterDao?.getCharacters()
//        assertEquals(characters[0].name, charactersTest?.get(0)?.name)
//    }

}