package com.vtsb.hipago.di.module

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.google.common.collect.Maps
import com.vtsb.hipago.domain.entity.TagType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapModule {


//    @Provides
//    @Singleton
//    @Named("imageURLCallbackHashMap")
//    fun provideImageURLCallbackHashMap(): ConcurrentHashMap<ImageTag?, GalleryImageRepository.ImageURLCallback?>? {
//        return ConcurrentHashMap<ImageTag?, GalleryImageRepository.ImageURLCallback?>()
//    }
//
//    @Provides
//    @Singleton
//    @Named("imageCallHashMap")
//    fun provideImageCallHashMap(): ConcurrentHashMap<ImageTag, Call> {
//        return ConcurrentHashMap<ImageTag?, Call?>()
//    }

    @Provides
    @Singleton
    @Named("numberTypeBiMap")
    fun provideNumberTypeBiMap(): BiMap<Byte, TagType> {
        val numberTypeBiMap = HashBiMap.create<Byte, TagType>()
        numberTypeBiMap[TagType.BEFORE.id] = TagType.BEFORE
        numberTypeBiMap[TagType.TYPE.id] = TagType.TYPE
        numberTypeBiMap[TagType.LANGUAGE.id] = TagType.LANGUAGE
        numberTypeBiMap[TagType.GROUP.id] = TagType.GROUP
        numberTypeBiMap[TagType.ARTIST.id] = TagType.ARTIST
        numberTypeBiMap[TagType.SERIES.id] = TagType.SERIES
        numberTypeBiMap[TagType.CHARACTER.id] = TagType.CHARACTER
        numberTypeBiMap[TagType.TAG.id] = TagType.TAG
        numberTypeBiMap[TagType.MALE.id] = TagType.MALE
        numberTypeBiMap[TagType.FEMALE.id] = TagType.FEMALE
        return numberTypeBiMap
    }

    @Provides
    @Singleton
    @Named("stringTypeBiMap")
    fun provideStringTypeBiMap(): BiMap<TagType, String> {
        val stringTypeBiMap = HashBiMap.create<TagType, String>()
        stringTypeBiMap[TagType.BEFORE] = TagType.BEFORE.otherName
        stringTypeBiMap[TagType.TYPE] = TagType.TYPE.otherName
        stringTypeBiMap[TagType.LANGUAGE] = TagType.LANGUAGE.otherName
        stringTypeBiMap[TagType.GROUP] = TagType.GROUP.otherName
        stringTypeBiMap[TagType.ARTIST] = TagType.ARTIST.otherName
        stringTypeBiMap[TagType.SERIES] = TagType.SERIES.otherName
        stringTypeBiMap[TagType.CHARACTER] = TagType.CHARACTER.otherName
        stringTypeBiMap[TagType.TAG] = TagType.TAG.otherName
        stringTypeBiMap[TagType.MALE] = TagType.MALE.otherName
        stringTypeBiMap[TagType.FEMALE] = TagType.FEMALE.otherName
        return stringTypeBiMap
    }

//    @Provides
//    @Singleton
//    @Named("languageNumberHashMap")
//    fun provideLanguageNumberHashMap(): HashMap<String, Long> {
//        return HashMap()
//    }

    @Provides
    @Singleton
    @Named("stringTypeBiMapInv")
    fun provideStringTypeBiMapInv(@Named("stringTypeBiMap") numberTypeBiMap: BiMap<TagType, String>): BiMap<String, TagType> {
        return numberTypeBiMap.inverse()
    }

    @Provides
    @Singleton
    @Named("tagTransformerBiMap")
    fun provideTagTransformerBiMap(): BiMap<String, String> {
        val tagTransformerBiMap: BiMap<String, String> = HashBiMap.create()
        tagTransformerBiMap["artist CG"] = "artistcg"
        tagTransformerBiMap["game CG"] = "gamecg"
        return tagTransformerBiMap
    }

    @Provides
    @Singleton
    @Named("tagTransformerBiMapInv")
    fun provideTagTransformerBiMapInv(@Named("tagTransformerBiMap") tagTransformerBiMap: BiMap<String, String>): BiMap<String, String> {
        return tagTransformerBiMap.inverse()
    }

    @Provides
    @Singleton
    @Named("tagNumberBiMap")
    fun provideTagNumberBiMap(): Array<BiMap<String, Long>> {
        val tagNumberBiMaps = LinkedList<BiMap<String, Long>>()
        repeat (10) {
            tagNumberBiMaps.add(HashBiMap.create())
        }
        return tagNumberBiMaps.toArray(arrayOfNulls(tagNumberBiMaps.size))
    }

//    @Provides
//    @Singleton
//    @Named("tagNumberSynchronizedBiMap")
//    fun provideTagNumberSynchronizedBiMap(@Named("tagNumberBiMap") tagNumberBiMap: Array<BiMap<String, Long>>): Array<BiMap<String, Long>> {
//        val tagNumberSynchronizedBiMap = LinkedList<BiMap<String, Long>>()
//        for (i in 0..tagNumberBiMap.size) {
//            tagNumberSynchronizedBiMap.add(
//                Maps.synchronizedBiMap(
//                    tagNumberBiMap[i]))
//        }
//        return tagNumberSynchronizedBiMap.toArray(arrayOfNulls(tagNumberSynchronizedBiMap.size))
//    }

    @Provides
    @Singleton
    @Named("tagLocalizationBiMap")
    fun provideTagLocalizationBiMap(): Array<BiMap<String, String>> {
        val tagLocalizationBiMap = LinkedList<BiMap<String, String>>()
        for (i in 0..8) {
            tagLocalizationBiMap.add(HashBiMap.create())
        }
        return tagLocalizationBiMap.toArray(arrayOfNulls(tagLocalizationBiMap.size))
    }

//    @Provides
//    @Singleton
//    @Named("tagLocalizationSynchronizedBiMap")
//    fun provideTagLocalizationSynchronizedBiMap(@Named("tagLocalizationBiMap") tagLocalizationBiMap: Array<BiMap<String, String>?>): Array<BiMap<String, String>>? {
//        val tagLocalizationSynchronizedBiMap = LinkedList<BiMap<String, String>>()
//        for (i in 0..tagLocalizationBiMap.size) {
//            tagLocalizationSynchronizedBiMap.add(
//                Maps.synchronizedBiMap(
//                    tagLocalizationBiMap[i]))
//        }
//        return tagLocalizationSynchronizedBiMap.toArray(arrayOfNulls(tagLocalizationSynchronizedBiMap.size))
//    }

    @Provides
    @Singleton
    @Named("tagLocalizationBiMapInv")
    fun provideTagLocalizationBiMapInv(@Named("tagLocalizationBiMap") tagLocalizationBiMap: Array<BiMap<String, String>>): Array<BiMap<String, String>>? {
        val tagLocalizationBiMapInv = LinkedList<BiMap<String, String>>()
        for (map in tagLocalizationBiMap) {
            tagLocalizationBiMapInv.add(map.inverse())
        }
        return tagLocalizationBiMapInv.toArray(arrayOfNulls(tagLocalizationBiMapInv.size))
    }



}