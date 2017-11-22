## ImagePicker
A simple library to select images from the gallery and camera.

## Screenshot

<img src="https://raw.githubusercontent.com/esafirm/android-image-picker/master/art/ss.gif" height="460" width="284"/>

## Download [![](https://jitpack.io/v/esafirm/android-image-picker.svg)](https://jitpack.io/#esafirm/android-image-picker)

Add this to your project's `build.gradle`

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

And add this to your module's `build.gradle` 

```groovy
dependencies {
	compile 'com.github.esafirm.android-image-picker:imagepicker:x.y.z'
	// for experimental rx picker
	compile 'com.github.esafirm.android-image-picker:rximagepicker:x.y.z'
}
```

change `x.y.z` to version in the [release page](https://github.com/esafirm/android-image-picker/releases)

## Usage

For full example, please refer to `sample`

### Start image picker activity
- Quick call

```java
ImagePicker.create(this) // Activity or Fragment
	    .start(REQUEST_CODE_PICKER);
``` 
- Complete options

```java
ImagePicker.create(this)
	.returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
	.folderMode(true) // folder mode (false by default)
	.folderTitle("Folder") // folder selection title
	.imageTitle("Tap to select") // image selection title
	.single() // single mode
	.multi() // multi mode (default mode)
	.limit(10) // max images can be selected (99 by default)
	.showCamera(true) // show camera or not (true by default)
	.imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
	.origin(images) // original selected images, used in multi mode
	.theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
	.enableLog(false) // disabling log
	.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
	.start(REQUEST_CODE_PICKER); // start image picker activity with request code
```                

- Get Intent

If you want to call it outside `Activity` or `Fragment`, you can simply get the `Intent` from the builder

```java
ImagePicker.create(activity).getIntent(context)

```

       
### Receive result


```java
@Override
if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
}
```


### Camera Only

```java
DefaultCameraModule cameraModule = new DefaultCameraModule() // or ImmediateCameraModule 
startActivityForResult(cameraModule.getIntent(context), RC_REQUEST_CAMERA);  
```

And for receiving the result:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RC_REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
        cameraModule.getImage(context, data, new OnImageReadyListener() {
            @Override
            public void onImageReady(List<Image> images) {
	    	// do what you want to do with the image ...
	    	// it's either List<Image> with one item or null (still need improvement)
            }
        });
    }
}
```


## Modification License

```
Copyright (c) 2016 Esa Firman

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

<<<<<<< HEAD
## Original License
=======
##Original License
>>>>>>> Update the readme 📚

[The Original Image Picker](https://github.com/nguyenhoanglam/ImagePicker)

[You can find the original lincense here ](https://raw.githubusercontent.com/esafirm/ImagePicker/master/ORIGINAL_LICENSE) 


