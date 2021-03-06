package com.example.pokedexapplication.model.API

class APIUtils {
  companion object {
    private val BASE_URL = "http://app11.lifetimetech.vn/pokedex/api/v1/"

    fun getAPIService(): IAPIService =
      APIClient.getClient(BASE_URL).create(IAPIService::class.java)
  }
}