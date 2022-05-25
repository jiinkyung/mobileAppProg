from django.shortcuts import render
from rest_framework import viewsets
from mobile_app.models import humanInfo
from mobile_app.serializers import hInfoSerializer

# Create your views here.
class hInfoViewSet(viewsets.ModelViewSet):
    queryset = humanInfo.objects.all()
    serializer_class = hInfoSerializer