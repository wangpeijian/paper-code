option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#3fb1e3', '#6be6c1'],
    // title: {
    //     text: 'PostgreSQL测试读事务对吞吐量的影响',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁']
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
        min: 40,
        max: 140,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [113.59024, 112.4504, 108.66648, 100.36544]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [97.82161, 97.76736, 96.96324, 92.74496]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [106.761557, 106.8298, 103.45517, 99.9996]
        },

        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [130.0568, 129, 123.74628, 116.9498]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [130.89789, 130.75902, 128.7921, 119.9]
        },

    ]
};