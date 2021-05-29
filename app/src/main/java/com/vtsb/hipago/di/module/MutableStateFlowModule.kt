package com.vtsb.hipago.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MutableStateFlowModule {

    // todo : change it to safe mechanism
    // instead of injecting value from outside,
    // get value from inside directly

    @Provides
    @Singleton
    @Named("tagLanguageMSF")
    fun provideTagLanguageMSF(): MutableStateFlow<String> =
        MutableStateFlow("korean")

//    @Provides
//    @Singleton
//    @Named("languageNumberMSF")
//    fun provideLanguageNumberMSF(): MutableStateFlow<Long> =
//        MutableStateFlow(-1)


    @Provides
    @Singleton
    @Named("useLanguageMSF")
    fun provideUseLanguageMSF(@Named("tagLanguageMSF") tagLanguageFSF: MutableStateFlow<String>): MutableStateFlow<String> =
        MutableStateFlow(tagLanguageFSF.value)

    @Provides
    @Singleton
    @Named("optionLRThumbnailMSF")
    fun provideOptionLRThumbnailMSF(): MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    @Provides
    @Singleton
    @Named("optionImagePreviewMSF")
    fun provideOptionImagePreviewMSF(): MutableStateFlow<Boolean> =
        MutableStateFlow(true)

    @Provides
    @Singleton
    @Named("optionGalleryViewTypeMSF")
    fun provideOptionGalleryViewTypeMSF(): MutableStateFlow<Int> =
        MutableStateFlow(1)


}