package com.vtsb.hipago.data.mapper

import com.vtsb.hipago.data.datasource.remote.entity.TagWithAmount
import com.vtsb.hipago.data.datasource.remote.service.GalleryService
import com.vtsb.hipago.data.datasource.remote.service.converter.ElementsConverter
import com.vtsb.hipago.data.datasource.remote.service.converter.ResponseBodyConverter
import okhttp3.ResponseBody
import org.jsoup.select.Elements
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryServiceMapper @Inject constructor(
    private val galleryService: GalleryService,
    private val responseBodyConverter: ResponseBodyConverter,
    private val elementsConverter: ElementsConverter,
) {

    fun getAllLanguages() =
        elementsConverter.toLanguageTagList(
            responseBodyConverter.toElements(
                galleryService.getAllLanguages().execute().body()!!))

    fun getAllTagAndAllURL(type: String): Pair<List<String>, List<TagWithAmount>> {
        val elements = responseBodyConverter.toElements(
            galleryService.getAllSpecificAlphabetTags(type, 'a').execute().body()!!)

        val receivedTagURLList: List<String> = elementsConverter.toTagURLList(elements)
        val tagWithAmountList: List<TagWithAmount> = elementsConverter.toTagWithAmountList(elements)

        return Pair(receivedTagURLList, tagWithAmountList)
    }

    fun getAllTag(url: String): List<TagWithAmount> =
        elementsConverter.toTagWithAmountList(
            responseBodyConverter.toElements(
                galleryService.getResponseBody(url).execute().body()!!))

    fun getDetailedGalleryBlock(id: Int, url: String) =
        elementsConverter.toGalleryBlockDetailed(
            getDetailedGalleryBlockElementRecursive(
                galleryService.getResponseBody(url).execute().body()!!), id
        )

    fun getDetailedGalleryBlockElementRecursive(responseBody: ResponseBody): Elements {
        val elements = responseBodyConverter.toElements(responseBody)
        val windowLocation = elementsConverter.toWindowLocation(elements)
        if (windowLocation != null) {
            return getDetailedGalleryBlockElementRecursive(
                galleryService.getResponseBody(windowLocation.substring(18)).execute().body()!!)
        }
        return elements
    }



}