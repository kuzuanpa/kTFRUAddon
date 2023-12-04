a=0
for i in $(cat genMaterialList)
 do
 
 if [ $a -eq 0 ] ;then
 zhCNName=$i
 fi
 if [ $a -eq 1 ] ;then
 enUSName=$i
 fi
 if [ $a -eq 2 ] ;then
 formula=$i
 fi
 if [ $a -eq 3 ] ;then
 meltpoint=$i
 fi
 if [ $a -eq 4 ] ;then
 boilpoint=$i
 a=-1
 echo "        //$zhCNName
        matList.$enUSName.registerC(++i,\"$enUSName\",\"$enUSName\",$meltpoint,$boilpoint,255,255,255,130,\"$formula\")
	.put();" >> matPreInit.java
echo ",/**$zhCNName**/$enUSName">>matList.java
if [ $boilpoint -lt 0 ];then
echo "        //$zhCNName
        flList.$enUSName.register(\"$enUSName\",\"$enUSName\",matList.$enUSName.get(), GAS);" >>flPreInit.java
echo ",/**$zhCNName**/$enUSName">>flList.java
elif [ $meltpoint -lt 0 ] ;then
echo "        //$zhCNName
        flList.$enUSName.register(\"$enUSName\",\"$enUSName\",matList.$enUSName.get(), LIQUID);" >>flPreInit.java
echo ",/**$zhCNName**/$enUSName">>flList.java
fi

 fi

 let a++
done
