var event_data_7d = /*[[${eventData7D}]]*/'noValue';
        var oneclick_data_7d = /*[[${oneClickData7D}]]*/'noValue';
        var bank_Oneclick_data_30d = /*[[${bankOneClickData30D}]]*/'noValue';
        var bank_event_data_30d = /*[[${bankEventData30D}]]*/'noValue';
         var reg_device_data_7d = /*[[${bankRegDeviceData30D}]]*/'noValue';
        
        
        $(document).ready(function() {
            google.charts.load('current', {'packages':['corechart', 'line', 'bar']});
            google.charts.setOnLoadCallback(drawAllEventsFor7D);
            //google.charts.setOnLoadCallback(drawBankOneClick30D);
            //google.charts.setOnLoadCallback(drawPieChart);
             
      		google.charts.setOnLoadCallback(drawBankOneClick30D);
      		google.charts.setOnLoadCallback(drawBankEvent30D);
      		google.charts.setOnLoadCallback(drawRegDevicePieChart);
      		
        });
        function drawAllEventsFor7D() {
        	google.charts.load('current', {'packages':['line']});
            var data = new google.visualization.DataTable();
          
               var chartData = [
		        
		      ]; 

              var data = new google.visualization.DataTable();
		      data.addColumn('string', 'Day');
		      data.addColumn('number', 'Event');
		      data.addColumn('number', 'One-Click');
		      
		      
		      Object.keys(oneclick_data_7d).forEach(function(key) {
                chartData.push([ key, oneclick_data_7d[key],event_data_7d[key]]);
            });
		      console.log(chartData);
		      data.addRows(chartData);
        var options = {
        	title: 'Event Count & One-Click setup count for last 7 days',
          	curveType: 'function',
          	legend: { position: 'bottom' },
	        hAxis: {
	          title: 'Date'
	        },
	        vAxis: {
	          title: 'Count'
	        },
	        colors: ['#a52714', '#097138'],
	        crosshair: {
	          color: '#000',
	          trigger: 'selection'
	        }
	      };
	      
	      	
            var chart = new google.charts.Line(document.getElementById('chart_div'));
            chart.draw(data, google.charts.Line.convertOptions(options));
        }
        
        
        
        function drawBankOneClick30D() {
	        var data = new google.visualization.DataTable();
          
               var chartData = []; 

              var data = new google.visualization.DataTable();
		      data.addColumn('string', 'Bank');
		      data.addColumn('number', 'One-Click');
		      //data.addColumn({type:'string', role:'style'});
		      
		      
		      Object.keys(bank_Oneclick_data_30d).forEach(function(key) {
                chartData.push([ key, bank_Oneclick_data_30d[key]]);
            });
		      console.log(chartData);
		      data.addRows(chartData);
	
	        var options = {
	          width: '100%',
	          legend: { position: 'none' },
	          colors: ['green', 'red', 'blue','green', 'red', 'blue','green', 'red', 'blue'],
	          chart: {
	            title: 'One-Click Setup coutn for last 30 days'
	            //subtitle: 'popularity by percentage'
	             },
	          axes: {
	            x: {
	              0: { side: 'bottom', label: 'Bank'} // Top x-axis.
	            },
	            y: {
	              0: { label: 'Count'} // Top x-axis.
	            }
	            
	          },
	          bar: { groupWidth: "25%" }
	        };
	
	        var chart = new google.charts.Bar(document.getElementById('bank_oneclick_chart_div'));
	        // Convert the Classic options to Material options.
	        chart.draw(data, google.charts.Bar.convertOptions(options));
	    };
	    
	    function drawBankEvent30D() {
	        var data = new google.visualization.DataTable();
          
               var chartData = []; 

              var data = new google.visualization.DataTable();
		      data.addColumn('string', 'Bank');
		      data.addColumn('number', 'Event');
		      
		      
		      Object.keys(bank_event_data_30d).forEach(function(key) {
                chartData.push([ key, bank_event_data_30d[key]]);
            });
		      console.log(chartData);
		      data.addRows(chartData);
	
	        var options = {
	          width: '100%',
	          legend: { position: 'bottom' },
	          chart: {
	            title: 'Event count for last 30 days'
	            
	             },
	          axes: {
	            x: {
	              0: { side: 'bottom', label: 'Bank'} // Top x-axis.
	            },
	            y: {
	              0: { label: 'Count'} // Top x-axis.
	            }
	            
	          },
	          bar: { groupWidth: "25%" }
	        };
	
	        var chart = new google.charts.Bar(document.getElementById('bank-event-chart_div'));
	        // Convert the Classic options to Material options.
	        chart.draw(data, google.charts.Bar.convertOptions(options));
	    };
	    
	    
	    function drawRegDevicePieChart() {
	       var data = new google.visualization.DataTable();
          
               var chartData = []; 

              var data = new google.visualization.DataTable();
		      data.addColumn('string', 'Bank');
		      data.addColumn('number', 'Event');
		      
		      
		      Object.keys(reg_device_data_7d).forEach(function(key) {
                chartData.push([ key, reg_device_data_7d[key]]);
            });
		      console.log(chartData);
		      data.addRows(chartData);
	
	        var options = {
	         width: '100%',
	          legend: { position: 'right' },
	          pieHole: 0.2,
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('device_reg_chart_div'));
	        chart.draw(data, options);
	    };
	    
	    
	    function generateRandomColor(){
		    let maxVal = 0xFFFFFF; // 16777215
		    let randomNumber = Math.random() * maxVal; 
		    randomNumber = Math.floor(randomNumber);
		    randomNumber = randomNumber.toString(16);
		    let randColor = randomNumber.padStart(6, 0);   
		    return `#${randColor.toUpperCase()}`
		}