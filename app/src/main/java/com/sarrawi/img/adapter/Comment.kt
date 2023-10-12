package com.sarrawi.img.adapter

//        holder.binding.imageView.setOnClickListener {
//            // عند النقر على الصورة، قم بتبديل حالة الشريط العلوي
//            isToolbarVisible = !isToolbarVisible
//
//            // يمكنك استخدام واجهة المستخدم الخاصة بك لإظهار أو إخفاء الشريط العلوي
//            // على سبيل المثال، إذا كنت تستخدم AppCompatActivity:
////            if (isToolbarVisible) {
////                (con as AppCompatActivity).supportActionBar?.show()
////
////            } else {
////                (con as AppCompatActivity).supportActionBar?.hide()
////            }
//
//            val uiFlags = if (isToolbarVisible) {
//                // إذا كان شريط الأدوات مرئيًا
//                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//            } else {
//                // إذا تم إخفاء شريط الأدوات
//                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//            }
//
//            (con as AppCompatActivity).window.decorView.systemUiVisibility = uiFlags
//            (con as AppCompatActivity).supportActionBar?.apply {
//                if (isToolbarVisible) {
//                    show()
//                } else {
//                    hide()
//                }
//
//            }
//        }
var isSystemUIVisible = true // تعيينها إلى true في البداية

//        holder.binding.imageView.setOnClickListener {
//            isSystemUIVisible = !isSystemUIVisible // قم بتبديل حالة الظهور للنظام
//
//            if (isSystemUIVisible) {
//                // إذا تم إظهار النظام، قم بإظهار شريط العنوان وشريط الملاحة
//                (con as AppCompatActivity).window.decorView.systemUiVisibility = (
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_VISIBLE // إظهار النظام
//                        )
//                (con as AppCompatActivity).supportActionBar?.show()
//            } else {
//                // إذا تم إخفاء النظام، قم بإخفاء شريط العنوان وشريط الملاحة
//                (con as AppCompatActivity).window.decorView.systemUiVisibility = (
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                        )
//                (con as AppCompatActivity).supportActionBar?.hide()
//            }
//        }