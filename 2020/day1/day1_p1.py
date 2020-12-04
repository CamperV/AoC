
with open('input.txt', 'r') as tf:
	nums = [int(l.strip()) for l in tf.readlines()]

for ind in range(len(nums)):
	curr_num = nums[ind]

	for other in nums[ind+1:]:
		sum = curr_num + other
		if sum == 2020:
			print(f'{curr_num} + {other} = 2020')
			print(f'{curr_num} * {other} = {curr_num*other}')
