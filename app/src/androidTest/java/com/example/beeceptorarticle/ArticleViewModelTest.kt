package com.example.beeceptorarticle

import com.example.beeceptorarticle.ViewModel.ArticleViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
class ArticleViewModelTest {

    @Mock
    private lateinit var apiClient: ApiEndPoint

    @Mock
    private lateinit var viewModel: ArticleViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArticleViewModel()
    }

    @Test
    fun getListResponse() {
        val response = runBlocking {
            apiClient.getArticleData()
        }
        Mockito.`when`(viewModel.getArticleFromApi()).thenReturn(response)
    }

    @Test
    fun getListDetailResponse() {
        val response = runBlocking {
            apiClient.getArticleDetailData(1)
        }
        Mockito.`when`(viewModel.getArticleDetailsFromApi(1)).thenReturn(response)
    }

}
