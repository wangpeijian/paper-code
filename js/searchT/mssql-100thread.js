option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    // title: {
    //     text: 'SQL Server测试读事务对吞吐量的影响',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '读未提交隔离级别+Redis锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁', '读未提交隔离级别+Java锁']
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['关闭事务','仅执行读操作', '读取数据后执行100ms业务', '读取数据后执行500ms业务'],
        name: '读事务状态'
    },
    yAxis: {
        type: 'value',
        min: 40,
        max: 130,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [77.11459,73.38786, 62.1291, 41.13508]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [107.38533,101.9011,95.59845, 77.33607]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [108.51394,105.69548,100.29625, 83.89173]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [109.85568,107.10315, 101.64432, 84.26607]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [120.74484,117.32014, 111.3021,96.88392 ]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [122.0847,121.62024,  115.30264, 99.24288]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [124.54296,124.25424,115.9908, 100.6096]
        },
    ]
};