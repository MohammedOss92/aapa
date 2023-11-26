package com.sarrawi.img

//    private fun setUpRv() {
//        if (isAdded) {
//            imgsViewModel.getAllImgsViewModel(ID).observe(viewLifecycleOwner) { imgs ->
//                imgAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//                if (imgs.isEmpty()) {
//                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
//                    imgsViewModel.getAllImgsViewModel(ID)
//                } else {
//                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
//
//                    // هنا قم بالحصول على البيانات المفضلة المحفوظة محليًا من ViewModel
//                    favoriteImagesViewModel.getAllFava().observe(viewLifecycleOwner) { favoriteImages ->
//                        val allImages: List<ImgsModel> = imgs
//
//                        for (image in allImages) {
//                            val isFavorite = favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
//                            image.is_fav = isFavorite // قم بتحديث حالة الصورة
//                        }
//
//                        imgAdapter.img_list = allImages
//
//                        if (binding.rvImgCont.adapter == null) {
//                            binding.rvImgCont.layoutManager = GridLayoutManager(requireContext(),2)
//                            binding.rvImgCont.adapter = imgAdapter
//                        } else {
//                            imgAdapter.notifyDataSetChanged()
//                        }
//                        if (currentItemId != -1) {
//                            binding.rvImgCont.scrollToPosition(currentItemId)
//                        }
//
//                    }
//                }
//
//                imgAdapter.onItemClick = { _, imgModel: ImgsModel,currentItemId ->
//                    if (imgsViewModel.isConnected.value == true) {
//
//                        clickCount++
//                        if (clickCount >= 2) {
//// بمجرد أن يصل clickCount إلى 2، اعرض الإعلان
//                            if (mInterstitialAd != null) {
//                                mInterstitialAd?.show(requireActivity())
//                            } else {
//                                Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                            }
//                            clickCount = 0 // اعيد قيمة المتغير clickCount إلى الصفر بعد عرض الإعلان
//
//                        }
//
//                        val directions = ThirdFragmentDirections.actionToFourFragment(ID, currentItemId,imgModel.image_url)
//                        findNavController().navigate(directions)
//
//
//                    } else {
//                        val snackbar = Snackbar.make(
//                            requireView(),
//                            "لا يوجد اتصال بالإنترنت",
//                            Snackbar.LENGTH_SHORT
//                        )
//                        snackbar.show()
//                    }
//                }
//            }
//        }
//    }

//Four
//    private fun setUpRv() {
//        if (isAdded) {
//            imgsViewmodel.getAllImgsViewModel(ID).observe(viewLifecycleOwner) { imgs ->
//                adapterLinRecy.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
//
//                if (imgs.isEmpty()) {
//                    // قم بتحميل البيانات من الخادم إذا كانت القائمة فارغة
//                    imgsViewmodel.getAllImgsViewModel(ID)
//                } else {
//                    // إذا كانت هناك بيانات، قم بتحديث القائمة في الـ RecyclerView
//
//                    // هنا قم بالحصول على البيانات المفضلة المحفوظة محليًا من ViewModel
//                    favoriteImagesViewModel.getAllFava().observe(viewLifecycleOwner) { favoriteImages ->
//                        val allImages: List<ImgsModel> = imgs
//
//                        for (image in allImages) {
//                            val isFavorite = favoriteImages.any { it.id == image.id } // تحقق مما إذا كانت الصورة مفضلة
//                            image.is_fav = isFavorite // قم بتحديث حالة الصورة
//                        }
//
//                        adapterLinRecy.img_list = allImages
//
//                        if (binding.rvImgCont.adapter == null) {
//                            binding.rvImgCont.layoutManager = LinearLayoutManager(requireContext())
//                            binding.rvImgCont.adapter = adapterLinRecy
//                        } else {
//                            adapterLinRecy.notifyDataSetChanged()
//                        }
//                        if (currentItemId != -1) {
//                            binding.rvImgCont.scrollToPosition(currentItemId)
//                        }
//
//                    }
//                }
//
//                adapterLinRecy.onItemClick = { _, imgModel: ImgsModel,currentItemId ->
//                    if (imgsViewmodel.isConnected.value == true) {
//
////                        clickCount++
////                        if (clickCount >= 2) {
////// بمجرد أن يصل clickCount إلى 2، اعرض الإعلان
////                            if (mInterstitialAd != null) {
////                                mInterstitialAd?.show(requireActivity())
////                            } else {
////                                Log.d("TAG", "The interstitial ad wasn't ready yet.")
////                            }
////                            clickCount = 0 // اعيد قيمة المتغير clickCount إلى الصفر بعد عرض الإعلان
////
////                        }
//
//                        val directions = FourFragmentDirections.actionFourFragmentToPagerFragmentImg(ID,currentItemId,imgModel.image_url)
//                        findNavController().navigate(directions)
//
//                    } else {
//                        val snackbar = Snackbar.make(
//                            requireView(),
//                            "لا يوجد اتصال بالإنترنت",
//                            Snackbar.LENGTH_SHORT
//                        )
//                        snackbar.show()
//                    }
//                }
//            }
//        }
//    }




//    fun adapterOnClick2() {
//        adapterLinRecy.onbtnClick = { it: ImgsModel, i: Int ->
//            val fav = FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url)
//
//            println("it.is_fav: ${it.is_fav}")
//            if (it.is_fav) {
//                it.is_fav = false
//                imgsffav.removeFavoriteImage(fav)
//
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
//                snackbar.show()
////                      setUpViewPager()
//
//                adapterLinRecy.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//                currentItemId = i
//                if (currentItemId != -1) {
//                    binding.rvImgCont.scrollToPosition(currentItemId)
//                }
//            } else {
//                it.is_fav = true
//                imgsffav.addFavoriteImage(fav)
//
//                imgsffav.updateImages()
//                val snackbar = Snackbar.make(view!!, "تم الاضافة", Snackbar.LENGTH_SHORT)
//                snackbar.show()
////                      setUpViewPager()
//
//                adapterLinRecy.notifyDataSetChanged()
//                println("it.is_fav: ${it.is_fav}")
//                currentItemId = i
//                if (currentItemId != -1) {
//                    binding.rvImgCont.scrollToPosition(currentItemId)
//                }
//            }
//            // تحقق من قيمة it.is_fav
//            println("it.is_fav: ${it.is_fav}")
////                  setUpViewPager()
//
//            adapterLinRecy.notifyDataSetChanged()
//            println("it.is_fav: ${it.is_fav}")
//            if (currentItemId != -1) {
//                binding.rvImgCont.scrollToPosition(currentItemId)
//            }
//        }
//
//        adapterLinRecy.onItemClick = { _, imgModel: ImgsModel, currentItemId ->
//
//            if (imgsViewmodel.isConnected.value == true) {
//                val directions = FourFragmentDirections.actionFourFragmentToPagerFragmentImg(
//                    ID,
//                    currentItemId,
//                    imgModel.image_url
//                )
//                findNavController().navigate(directions)
//            } else {
//                val snackbar = Snackbar.make(
//                    requireView(),
//                    "لا يوجد اتصال بالإنترنت",
//                    Snackbar.LENGTH_SHORT
//                )
//                snackbar.show()
//            }
//
//            val directions = FourFragmentDirections.actionFourFragmentToPagerFragmentImg(
//                ID,
//                currentItemId,
//                imgModel.image_url
//            )
//            findNavController().navigate(directions)
//
//        }
//
//    }

//    fun adapterOnClick() {
//        adapterLinRecy.onItemClick = { _, imgModel: ImgsModel, currentItemId ->
//            if (imgsViewModel.isConnected.value == true) {

//            clickCount++
//            if (clickCount >= 2) {
//                // بمجرد أن يصل clickCount إلى 2، اعرض الإعلان
//                if (mInterstitialAd != null) {
//                    mInterstitialAd?.show(requireActivity())
//                } else {
//                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                }
//                clickCount = 0 // اعيد قيمة المتغير clickCount إلى الصفر بعد عرض الإعلان
//
//            }


//            val directions = ThirdFragmentDirections.actionToFourFragment(ID, currentItemId, imgModel.image_url)
//            findNavController().navigate(directions)
//        }
//        else {
//                val snackbar = Snackbar.make(
//                    requireView(),
//                    "لا يوجد اتصال بالإنترنت",
//                    Snackbar.LENGTH_SHORT
//                )
//                snackbar.show()
//            }


//        adapterLinRecy.onbtnClick = { it: ImgsModel, i: Int ->
//            if (it.is_fav) {
//                // إذا كانت الصورة مفضلة، قم بإلغاء الإعجاب بها
//                it.is_fav = false
//                imgsffav.removeFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))
//                imgsffav.updateImages()
//                imgsffav.getFavByIDModels(it.id!!)
//                val snackbar = Snackbar.make(view!!, "تم الحذف", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//            } else {
//                // إذا لم تكن الصورة مفضلة، قم بإضافتها للمفضلة
//                it.is_fav = true
//                imgsffav.addFavoriteImage(FavoriteImage(it.id!!, it.ID_Type_id, it.new_img, it.image_url))
//                imgsffav.updateImages()
//                imgsffav.getFavByIDModels(it.id!!)
//                val snackbar = Snackbar.make(view!!, "تم الإضافة", Snackbar.LENGTH_SHORT)
//                snackbar.show()
//            }
//            // تحقق من قيمة it.is_fav
//            println("it.is_fav: ${it.is_fav}")
//            // تحديث RecyclerView Adapter
//            adapterLinRecy.notifyDataSetChanged()
//        }
//    }