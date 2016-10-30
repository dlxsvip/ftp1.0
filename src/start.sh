#!/bin/bash

echo -e "\033[32m-----------------------------\033[0m"
echo -e "\033[32m【get】: 下载                 \033[0m"
echo -e "\033[32m【put】: 上传                 \033[0m"
echo -e "\033[32m【  q】: 退出                 \033[0m"
echo -e "\033[32m-----------------------------\033[0m"



ftpFun(){
	read -p "参数:" txt
	if [ "$txt" == "" ];then
		echo ""
	elif [[ "$txt" == "q" || "$txt" == "-q"  ]];then
		exit 0
	elif [[ "$txt" == "get" || "$txt" == "put" ]];then
		java -jar `dirname $0`/lib/ftp1.0.jar "$txt"
	else
		echo ""
	fi
	ftpFun
}



case "$1" in
	-q|quit)
		exit 0 ;;
	 *)
		ftpFun ;;
esac



