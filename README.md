# Yuh Android Library
[![](https://jitpack.io/v/wawahuy/Yuh-Android-Library.svg)](https://jitpack.io/#wawahuy/Yuh-Android-Library)
## Mục tiêu v1.2.x
Xây dựng thư viện Graphics
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
	        implementation 'com.github.wawahuy:Yuh-Android-Library:[Chưa có version nào build]'
	}
```

## Tài liệu Cơ Bản

### Khởi tạo
- Để có thể làm việc hoàn chỉnh, cần khởi tạo App ít nhất một lần từ bất kì Activity được khởi động
```java
    App.getInstance().init(this);
```

### Model
- Ví dụ mẫu model:
```java
    public class LuotChoi extends Model {
        @JsonName
        public int id;
        
        @JsonName
        public int diem;
        
        /// v1.1.0-dev
        /// Hỗ trợ chứa model con
        @JsonName(type = JsonName.Type.Model, clazz = NguoiChoi.class)
        public NguoiChoi nguoiChoi;

        /// Hỗ trợ chứa modelmanager
        @JsonName(type = JsonName.Type.ModelManager, clazz = CTLuotChoi.class)
        public ModelManager<CTLuotChoi> ctLuotChoi;
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
        protected void OnCreate() {
            /// variable "view" is protected on presenter
            view.testView();
        }
        
        
        @Override
        protected void OnResume(Model model){
            /// Khi activity được tạo lại
            /// cần presenter.postDataSaved(data); ở onDestroy() activity
            /// dữ liệu sẽ được lưu lại trên presenter và gửi lại ở đây
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
    Khi câu hình hostname một khi các request được gửi đi không chứa giao thức sẽ
    được tự dộng thêm hostname này
```java
    APIConfig.setHostname("http://192.168.1.130:8000/api");
```

- Đồng bộ GET:
```java
    APIOutput output = APIProvider.GET("/test");
```

- Đồng bộ POST:
```java
    ApiParameters params = new ApiParameters();
    params.add("key", "value");
    APIOutput output = APIProvider.POST("/testpost", params);
```

- ApiParameters:
    Đối tượng này cho phép bạn gửi dữ liệu bao gồm Text, File, Bitmap và Bytes.

- APIProvider.Output chứa 3 thuộc tính:
    + Status
    + Message
    + Data có kiểu (JsonObject| JsonArray)
    + DataString
    + Uri
    + ResponseCode (Là code của giao thức HTTP)
    
- Chuyển dổi  APIOutput sang Model và ModelManager
```java
    APIOutput output = ...;
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
 
- Người đánh chặn ApiIntercept:
```java
    ApiIntercept intercept = new ApiIntercept() {
        @Override
        public void OnHeaderInject(HttpURLConnection connection) {
            /// Trước khi gửi request
            /// Nếu là phương thức POST, dữ liệu chưa được thêm
        }

        @Override
        public void OnResult(ApiOutput apiOutput) {
            /// Khi nhận được Output
        }
    };

    ApiConfig.addIntercept(intercept);
```

- Thêm xác thực Json Web Token (Jwt):
```java
    ApiConfig.setAuthenticate(new JWTAuthenticate(token));
```
  + Tấc cả request tiếp theo sẽ tự động được thêm xác thực.
  + Để hủy có thể gán null
  + Tạm dưng xác thực sử dụng ```ApiConfig.getAuthenticate().setStatus(false)```
   
