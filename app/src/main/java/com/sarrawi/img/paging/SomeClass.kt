package com.sarrawi.img.paging

import com.sarrawi.img.Api.ApiService

class SomeClass(private val apiService: ApiService) {
    // حدد القيمة الأولية لـ ID_Type_id
    private var ID_Type_id: Int = 0

    // إنشاء مثيل من الفئة ImgPaging وتمرير قيمة ID_Type_id
    private val imgPaging = ImgPaging(apiService, ID_Type_id)

    // عندما ترغب في تغيير قيمة ID_Type_id، قم بتعيينها
    fun changeID_TypeId(newID: Int) {
        ID_Type_id = newID
        // يمكنك أيضًا تحديث مثيل ImgPaging بالقيمة الجديدة إذا كنت بحاجة إلى ذلك
        imgPaging.updateID_TypeId(newID)
    }
}
