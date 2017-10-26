window.onload = function() {

  var columnNode = document.getElementById('column'),
      myColumn = echarts.init(columnNode);

  //获取 URL 中的参数
  var params = {}, reg = /([^\=\?\&]+)\=([^&]+)/;
  var url = window.location.search;
  while(reg.test(url)){
    url = url.replace(reg, function(input, $1, $2){
      params[$1] = $2;
      return ""
    });
  }

  function getMin(arr) {
    return Math.min.apply(Math,arr);    
  }

  function remove(thisNode) {
    thisNode.parentNode.removeChild(thisNode);
  }

  var num = 10, yData=[];
  while(num--){
    yData.push({
      textStyle: {
        fontSize: 32
      }
    });
  }

  /*
  Ajax方法解释
  第2个参数就是请求的 Action
  第3个参数就是传给后台的参数，那些字段名我都是根据你文档的写的，如果报错的话，注意字段名一不一样！！！
  */

  Ajax('get', '/school/evaluation/getSortedEvaluation', {studentId: params.studentId, startTime: params.startTime?params.startTime:'', endTime: params.endTime?params.endTime:''}, function(data){
    if(data.success) {
      var value = [], seriesData = [], result = data.result;
      if(result) {
        result.map(function(val, index) {
          if(val['sum(score)'] && val.name) {         
            value.push(val['sum(score)']);
            seriesData.push(val.name); 
          }
        });      
      }
      if (value && seriesData ) {
        var xData = [];
        for(var i=0, len = value.length; i < len; i++){
          xData.push({            
            //value: (function (str) {
            //    return len>7 ? str.split("").join("\n") : str;
           //   })(seriesData[i]),
	    value: seriesData[i], 
            textStyle: {
              fontSize: 32
            },
          });
        }      
      }
      var columnOption = {
         title : {
          text: data.result ? null : '暂无数据',
          subtextStyle: {
            color: 'black',
            fontSize: 32
          }, 
          itemGap: 20,
          x:'center',
          textStyle: {
            fontSize: 40  
          }
        },
        dataZoom: [{
          type: 'inside',
          realtime: true,
          start: 0,
          end: value.length > 7 ? 50 : 100
        }],

        color: ['#3398DB'],
        tooltip : {
          trigger: 'axis',
          axisPointer : {      
            type : 'shadow'        
          },
          textStyle: {
            fontSize: 42  
          }
        },
        grid: {
          left: '6%',
          bottom: '3%',
          top: '15%',
          containLabel: true,
          containLabel: true
        },
        xAxis : [{
          data: seriesData ? xData : null,
          axisTick: {
            alignWithLabel: true
          },
        }],
        yAxis : [{
          type : 'value',
          name:'分数',
          nameTextStyle: {
              fontSize: 36  
          },
          nameGap: 30,
          data: yData 
        }],
        series : [
          {
          name: '分数',
          type: 'bar',
          barWidth: 80,
          data: value?value:'',
          label: {
              normal: {
                show: true,
                position: 'top',
             }
            },
          }
        ],
        textStyle: {
          fontSize: 40
        }
      };
      myColumn.setOption(columnOption);  
    } else {
      remove(columnNode); 
    }
  }, function(error){
    console.log(error);
  });
}
