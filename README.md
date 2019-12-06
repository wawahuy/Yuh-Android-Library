# Yuh Android Library
[![](https://jitpack.io/v/wawahuy/Yuh-Android-Library.svg)](https://jitpack.io/#wawahuy/Yuh-Android-Library)
## Install
- build.gradle (Project:.....)
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

- build.gradle (Module:.....)
```
	dependencies {
	        implementation 'com.github.wawahuy:Yuh-Android-Library:v1.0.0'
	}
```

## Tài liệu

### Model
- Ví dụ mẫu model:
```java
    public class CauHoi extends Model {
    
        @JsonName
        private int id;
    
        @JsonName
        private String cauhoi;
    
        public String getCauhoi() {
            return cauhoi;
        }
    
        public void setCauhoi(String cauhoi) {
            this.cauhoi = cauhoi;
        }
    }
```

- Chuyển nó sang JSON String:
```java
    CauHoi cauHoi = new CauHoi();
    String json = cauHoi.toJson();
```

- Từ Json String sang MODEL:
```java
    CauHoi cauHoi = CauHoi.ParseJson(CauHoi.class, strJson);
```

### Model Manager
- Chuyển đổi giữa json array string sang model manager và ngược lại:
```java
    String strJsonArr = "...json array...";
    ModelManager<CauHoi> cauHoiModelManager = ModelManager.ParseJSON(CauHoi.class, strJsonArr);
```

### Presenter & View
- VD Presenter
```java
    public class TestPresenter extends Presenter<TestPresenter.View> {
        public TestPresenter(Context view) {
            super(view);
        }

        @Override
        protected void OnStart() {
            /// variable "view" is protected on presenter
            view.testView();
        }

        public interface View {
            /// init method on View
            void testView();
        }
    }
```

- VD View
```java
    public static class TestView extends Activity implements TestPresenter.View {
        TestPresenter presenter;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            presenter = new TestPresenter(this);
            presenter.Start();
        }

        @Override
        public void testView() {
            //// UI Update //////////
        }
    }
```


### API Provider
- Cấu hình HOST
```java
    APIProvider.SetHost("http://192.168.1.130:8000/api");
```

- Đồng bộ GET:
```java
    APIProvider.Output output = APIProvider.GET("/test");
```

- Đồng bộ POST:
```java
    Uri.Builder params = new Uri.Builder();
    params.appendQueryParameter("key", "value");
    APIProvider.Output output = APIProvider.POST("/testpost", params);
```

- APIProvider.Output chứa 3 thuộc tính:
    + Status
    + Message
    + Data có kiểu (JsonObject| JsonArray)
    
- Chuyển dổi  APIProvider.Output sang Model và ModelManager
```java
    APIProvider.Output output = ...;
    if(output.isDJObject()){
        CauHoi cauHoi = output.toModel(CauHoi.class);
    }
    else {
        ModelManager<CauHoi> cauHoiModelManager = output.toModelManager(CauHoi.class);
    }
```

- Xữ lí bất đồng bộ GET:
```java
    APIProvider.Async.GET("/test").Then(new APIProvider.Async.Callback() {
        @Override
        public void OnAPIResult(APIProvider.Output output, int requestCode) {
            ///---------- Result ---------
        }
    });
```

- Xữ lí bất đồng bộ POST:
```java
    APIProvider.Async.POST("/test")
            .AddParam("key1", "v1")
            .AddParam("key2", "v2")
            .AddParam("keyN", "vN")
            .Then(new APIProvider.Async.Callback() {
                @Override
                public void OnAPIResult(APIProvider.Output output, int requestCode) {
                    ///---------- Result ---------
                }
            });
```


### Loader & CustomLoader
- Ứng dụng xây dựng phần Loading cho app hoặc game
- VD
```java
    new CustomLoader(){
        @Override
        protected void OnUpdateProgress(int p) {
           //// UPDATE BAR ////
           //// Được gọi trên thread UI
        }

        @Override
        protected void OnUpdateText(String text) {
           //// UDPDATE TEXT ////
           /// Được gọi trên thread UI
        }

        @Override
        protected void OnStartLoad() {
            /// Thêm các thứ cần Load
            /// Chúng ta sẽ nói về AddLoad sao
            /// VD
            this.AddLoad(new Load<ModelManager<CHDiemCauHoi>>("Load config answer...", "Load config answer error"){
                @Override
                protected ModelManager<CHDiemCauHoi> OnRun() {
                    return chDiemCauHoi = APIProvider.GET(APIUri.CAU_HINH_CAU_HOI).toModelManager(CHDiemCauHoi.class);
                }
            });
        }

        @Override
        protected void OnCompleteLoad() {
            ///// COMPLETE LOAD /////
            /// Được gọi trên thread UI            
        }
    }.start();
```

- Để quản lý các thứ cần Load bạn phải gọi phương thức AddLoad, AddLoad yêu cầu truyền vào
một đối tượng Load, đối tượng này chứa 2 thông tin cơ bản là
    + Nội dung thông báo trước khi load
    + Nội dung thông báo khi load xong
```java
    new Load<KieuTraVe>("Thông Báo Trước Khi Load", "Thông Báo Sau Khi Load Xong"){
        @Override
        protected KieuTraVe OnRun() {
            /// Bất đồng bộ
            return tra_ve_doi_tuong_duoc_load;
        }
    }
```
Đối tượng được Return ở OnRun dùng để kiểm tra xem có load thành công chưa, và để kiểm tra xem
một đối tượng có được load chưa ta gọi phương thức của Load như
```java
    .AddErrorNull()
```
Kiểm tra đối tượng khác NULL hoặc tự định nghĩa
```java
    .AddError(new Load.CBError() {
        @Override
        public boolean error(Object o) {
            return false;
        }
    })
```
    