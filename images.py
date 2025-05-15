def get_image_urls(category):
    """
    Return URLs for the pre-fetched stock photos based on category.
    
    Parameters:
    category (str): Category of images to return
    
    Returns:
    list: List of image URLs
    """
    image_urls = {
        "medical heart illustrations": [
            "https://pixabay.com/get/g032fc588dbffe0c604fad04fd06db0430752c45b80e19d3339de1bec529c8576858458666d97d78cea340b29790d361998a53eb542a908154b993b50d8b2cf98_1280.jpg",
            "https://pixabay.com/get/g63d8692e4de32efcf1fae9b376392ffd7b3da7be4145d80266b14b6423e7129e74a3c84c592c090f911e5592a3fa6cd112f3194d24fe493a99d2b08a57685bf2_1280.jpg",
            "https://pixabay.com/get/g87569c49ec2857e3caee1d9c974eccc1e715657166a624907255619b9f54430e2b4bd83821b0875e63b06b86178c0481d05301ccbf94fe51c5c00ba2e76538d5_1280.jpg"
        ],
        "healthy lifestyle images": [
            "https://pixabay.com/get/g3e55b6e1285a623943a285dbd4a1944b4a2f2e37f8bdc5ed068ab0bd0ec25bd44533fddfcb39a96bded2a20895f2ed6aae1bd3bda8935abc29ed29f6fed82fc5_1280.jpg",
            "https://pixabay.com/get/g5624a9c23336655b04e13a0a0f6318aeef92862ddc2603f40eff4baf435845e73167d9c379abd18b4f049d2e7fb5e93a97df9d7b8bce7759aadde92d06ac607c_1280.jpg",
            "https://pixabay.com/get/g1df86519f32cec2d955cbe7137b67861ffd462366fe6edcdf8cac67fe947adb85c6c0dd532de1be97afb8ddac8f3e26f53ff529ac2f5b064cebf202218888c6c_1280.jpg"
        ],
        "heart health related": [
            "https://pixabay.com/get/g5269e1f1e363163f68427061ac73190ca3dcb77f164c46dc584747de6aa54d0a5728ff62f837ce0f59e7fbaf878073f4625cf677a9629dbbdc64fae8665a54d1_1280.jpg",
            "https://pixabay.com/get/g4a5a37fd025b2b3a11607e2e74ac04ef8d510fcd0555af94126da7af167064f43b283eaab333f5e20ecef04de31ef76b44d87fac0240a829a23fe07ac72dcecb_1280.jpg"
        ]
    }
    
    return image_urls.get(category, [])
