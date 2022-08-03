# Consolidation files in database "vmv_consolidation_files" tables
/usr/hdp/current/spark2-client/bin/spark-submit \
  --name 'Consolidation' \
  --class 'apps.ConsolidationTableApp' \
  --master "yarn" \
  --deploy-mode "cluster" \
  --queue "heavy" \
  --num-executors 7 \
  --driver-memory 14G \
  --executor-cores 5 \
  --executor-memory 14g \
  --conf spark.eventLog0.enabled=false \
  --conf spark.yarn.submit.waitAppCompletion=true \
  --conf spark.dynamicAllocation.enabled=false \
  --conf spark.rpc.message.maxSize=512 \
  --conf spark.driver.maxResultSize=4g \
  'hdfs://hdp3is/user/vmv/apps/spark/SparkUtilities-sparkConsolidationFiles-assembly-1.0.jar' \
  '{"nameDB": "vmv_consolidation_files", "tables": ["users_l2_test_consolidation"] }'
#
/usr/hdp/current/spark2-client/bin/spark-submit \
  --name 'ConsolidationPartitionTable' \
  --class 'apps.ConsolidationTableApp' \
  --master "yarn" \
  --deploy-mode "cluster" \
  --queue "heavy" \
  --num-executors 7 \
  --driver-memory 14G \
  --executor-cores 5 \
  --executor-memory 14g \
  --conf spark.eventLog0.enabled=false \
  --conf spark.yarn.submit.waitAppCompletion=true \
  --conf spark.dynamicAllocation.enabled=false \
  --conf spark.rpc.message.maxSize=512 \
  --conf spark.driver.maxResultSize=4g \
  'hdfs://hdp3is/user/vmv/apps/spark/SparkUtilities-sparkConsolidationFiles-assembly-1.0.jar' \
  '{"nameDB": "vmv_consolidation_files", "tables": ["t_blacklist"] }'

# Consolidation files in database "dfp" tables
/usr/hdp/current/spark2-client/bin/spark-submit \
  --name 'Consolidation' \
  --class 'apps.ConsolidationTableApp' \
  --master "yarn" \
  --deploy-mode "cluster" \
  --queue "heavy" \
  --num-executors 7 \
  --driver-memory 14G \
  --executor-cores 5 \
  --executor-memory 14g \
  --conf spark.eventLog0.enabled=false \
  --conf spark.yarn.submit.waitAppCompletion=true \
  --conf spark.dynamicAllocation.enabled=false \
  --conf spark.rpc.message.maxSize=512 \
  --conf spark.driver.maxResultSize=4g \
  'hdfs://hdp3is/user/vmv/apps/spark/SparkUtilities-sparkConsolidationFiles-assembly-1.0.jar' \
  '{"nameDB": "dfp" }'
