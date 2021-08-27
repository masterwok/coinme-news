package com.masterwok.coinme.di.modules

import com.masterwok.coinme.features.news.di.NewsComponent
import dagger.Module

@Module(subcomponents = [NewsComponent::class])
class SubcomponentModules