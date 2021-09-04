option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    // title: {
    //     text: 'SQL Server事务不同冲突系数的吞吐量趋势',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '读未提交隔离级别+Redis锁',
            '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁', '读未提交隔离级别+Java锁']
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
        data: ['0.6', '0.8', '1.0', '1.2', '1.4'],
        name: 'zipf系数'
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
            data: [104.15886, 95.43045, 73.6668, 50.4695, 43.84827]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [117.21424, 113.78961, 101.9011, 63.18369, 52.35296]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [119.19873, 116.4652, 105.69548, 65.03796, 52.49106]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [120.17148, 118.59081, 107.10315, 67.333, 54.31762]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [134.78076, 132.92868, 117.32014, 78.944, 65.78912]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [136.8216, 134.48344, 121.62024, 79.41404, 67.79124]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [137.83, 135.25232, 124.30854, 80.00816, 69.188]
        },
    ]
};