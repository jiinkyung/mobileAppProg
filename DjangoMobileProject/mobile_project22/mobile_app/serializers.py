from rest_framework import serializers
from .models import humanInfo

class hInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = humanInfo
        fields = ['name', 'phone_number', 'address']