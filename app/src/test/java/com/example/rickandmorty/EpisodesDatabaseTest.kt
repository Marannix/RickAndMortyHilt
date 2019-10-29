package com.example.rickandmorty

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EpisodesDatabaseTest {


    // TODO: Improve test after creating usecase and repository
//    private var firstCharacterEpisode: EpisodeResponse = EpisodeResponse(
//        1, "1", "1", "Pilot", "December 2, 2013", "S01E01"
//    )
//    private var secondCharacterEpisode: EpisodeResponse = EpisodeResponse(
//        2, "2", "2", "Lawnmower Dog", "December 9, 2013", "S01E02"
//    )
//
//    private val applicationContext = ApplicationProvider.getApplicationContext<Context>()
//    private lateinit var episodesDao: EpisodesDao
//    private lateinit var database: ApplicationDatabase
//
//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        ApplicationDatabase.DatabaseProvider.TEST_MODE = true
//        database = ApplicationDatabase.DatabaseProvider.getInstance(applicationContext)!!
//        episodesDao = database.episodeDao()
//    }
//
//    @After
//    @Throws(Exception::class)
//    fun closeDB() {
//        ApplicationDatabase.DatabaseProvider.getInstance(applicationContext)?.close()
//    }

//    @Test
//    fun should_insert_a_episode() {
//        episodesDao.storeEpisodesInDb(firstCharacterEpisode)
//        val getEpisodesFromDb = episodesDao.getEpisodesFromDb(firstCharacterEpisode.id)
//        Assert.assertEquals(firstCharacterEpisode.id, getEpisodesFromDb[0].id)
//    }

//    @Test
//    fun should_clear_episodes() {
//        episodesDao.flushEpisodeData()
//        Assert.assertTrue(episodesDao.getAllEpisodes().isEmpty())
//    }
//
//    @Test
//    fun should_insert_two_different_character_episodes() {
//        episodesDao.storeEpisodesInDb(firstCharacterEpisode)
//        episodesDao.storeEpisodesInDb(secondCharacterEpisode)
//        val getAllEpisodes = episodesDao.getAllEpisodes()
//        Assert.assertEquals(firstCharacterEpisode.id, getAllEpisodes[0].id)
//        Assert.assertEquals(secondCharacterEpisode.id, getAllEpisodes[1].id)
//        Assert.assertTrue(firstCharacterEpisode.id != getAllEpisodes[1].id )
//    }
}