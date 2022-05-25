from django.urls import path, include

app_name = 'mobile_app'
urlpatterns = [
     path('', include('rest_framework.urls', namespace='rest_framework_category'))
]