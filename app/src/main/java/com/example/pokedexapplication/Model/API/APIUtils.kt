package com.example.pokedexapplication.Model.API

class APIUtils {
  companion object {
    private val BASE_URL = "http://app11.lifetimetech.vn/pokedex/api/v1/"

    fun getAPIService() =
      APIClient.getClient(BASE_URL).
  }
}