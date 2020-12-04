
with open('input.txt', 'r') as tf:
    nums = [int(l.strip()) for l in tf.readlines()]

for ind, curr_num in enumerate(nums):

    for other_ind, other in enumerate(nums[ind+1:]):
        curr_sum = curr_num + other

        for final_ind, final in enumerate(nums[other_ind+1:]):
                if curr_sum + final == 2020:
                        print(f'{curr_num} + {other} + {final} = 2020')
                        print(f'{curr_num} * {other} * {final} = {curr_num*other*final}')
