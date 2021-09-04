option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    // title: {
    //     text: 'MySQL测试读事务对吞吐量的影响',
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
        axisLabel: {
            fontSize: 16,
        },
        type: 'category',
        boundaryGap: false,
        data: ['关闭事务', '仅执行读操作', '读取数据后执行100ms业务', '读取数据后执行500ms业务'],
        name: '读事务状态'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'value',
        min: 50,
        max: 130,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [100.983894, 95.43096, 79.05632, 60.4469]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [105.16093, 100.85193, 94.46136, 77.3808]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [105.86016, 104.3058, 99.26917, 87.0941]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [106.66089, 104.76576, 100.37208, 89.3031]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [126.2, 121.2, 115.7, 87.63552]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [126.4, 125.4, 117.8, 101.58984]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [127, 126.3, 118.7, 103.32405]
        },
    ]
};